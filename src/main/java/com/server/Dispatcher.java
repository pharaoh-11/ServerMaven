package com.server;

import com.data.Request;
import com.data.Response;
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

    private static final String RESPONSE_500 = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";

    private Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public Response handleRequest(Request request) {
        ConcreteHandler concreteHandler;
        Response response;
        try {
            concreteHandler = router.findNeededHandler(request);
            LOG.info("Dispatcher received correspond handler");
        } catch (NoSuchHandlerException e) {
            LOG.error("Handler for accepted request doesn't exist");
            return send500();
        }
        response = concreteHandler.handleQuery(request);
        return response;
    }

    private static Response send500() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        Response response = new Response();

        ((ObjectNode) rootNode).put("status", "500");
        ((ObjectNode) rootNode).put("statusText", "Internal Server Error");
        try {
            response.setBody(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        response.setHead(RESPONSE_500);
        return response;
    }
}
