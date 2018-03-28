package com.server;

import com.data.Request;
import com.data.Response;
import com.entity.DBIntern;
import com.exception.NoSuchHandlerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.handlers.ConcreteHandler;
import com.server.router.Router;
import org.apache.log4j.Logger;

public class Dispatcher {
    private static final Logger LOG = Logger.getLogger(Dispatcher.class);

    private static final String RESPONSE_400 = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";

    private Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public Response handleRequest(Request request, DBIntern dbIntern) {
        ConcreteHandler concreteHandler;
        Response response;
        try {
            concreteHandler = router.findNeededHandler(request);
            LOG.info("Dispatcher received correspond handler");
        } catch (NoSuchHandlerException e) {
            LOG.error("Handler for accepted request doesn't exist");
            return send400();
        }
        response = concreteHandler.handleQuery(request, dbIntern);
        return response;
    }

    private static Response send400() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();

        ((ObjectNode) rootNode).put("status", "400");
        ((ObjectNode) rootNode).put("statusText", "Wrong request");
        String httpResponse = null;
        try {
            httpResponse = RESPONSE_400 +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Response response = new Response();
        response.setHead(httpResponse);
        return response;
    }
}
