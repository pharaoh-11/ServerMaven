package com.server.controller;

import com.data.Request;
import com.data.Response;
import com.entity.Entity;
import com.entity.Group;
import com.entity.Intern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.server.controller.db.DataBase;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private static final Logger LOG = Logger.getLogger(Controller.class);

    private static final String RESPONSE_200 = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_201 = "HTTP/1.1 201 Created\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_202 = "HTTP/1.1 202 No Content\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_FOR_OPTIONS = "HTTP/1.1 204 No Content\r\n"+
            "Access-Control-Allow-Credentials: true\r\n"+
            "Access-Control-Allow-Headers: Content-Type\r\n"+
            "Access-Control-Allow-Methods: GET, HEAD, PUT, PATCH, POST, DELETE, OPTIONS\r\n" +
            "Access-Control-Allow-Origin: * \r\nConnection: keep-alive\r\n" +
            "Vary: Origin, Access-Control-Request-Headers\r\n\r\n";
    private static final String RESPONSE_400 = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_404 = "HTTP/1.1 404 Not Found\r\nContent-Type: application/json; charset=utf-8\r\n\r\n";
    private static final String RESPONSE_500 = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";

    private DataBase dataBase;

    public Controller(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Response getAllInterns(Request request) {
        Response response = new Response();
        ArrayList<Intern> interns = dataBase.getAllInterns();
        return getResponseBody(response, interns);
    }

    public Response getGroups(Request request) {
        Response response = new Response();
        ArrayList<Group> groups = dataBase.getGroups();
        return getResponseBody(response, groups);
    }

    private Response getResponseBody(Response response, ArrayList<? extends Entity> list) {
        try {
            response.setBody(View.viewBody(list));
        } catch (JsonProcessingException e) {
            LOG.error("Didn't create response body");
            e.printStackTrace();
            response.setHead(RESPONSE_500);
        }
        response.setHead(RESPONSE_200);
        LOG.info("Method GET was processed");

        return response;
    }

    public Response getInternById(Request request) {
        Response response = new Response();
        try {
            int id = request.getQuery().get("interns");
            response.setBody(View.viewBody(dataBase.getInternsById(id)));

            if (response.isBodyNull()) {
                return send404();
            }
            response.setHead(RESPONSE_200);
            LOG.info("Method GET was processed");
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Didn't create response body");
        }
        return response;
    }

    public Response options(Request request) {
        Response response = new Response();
        response.setHead(RESPONSE_FOR_OPTIONS);
        LOG.info("Answer to options query method");
        return response;
    }

    public Response postNewIntern(Request request) {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        Intern intern;
        String body = request.getBody();
        try {
            intern = mapper.readValue(body, Intern.class);
            if(dataBase.postIntern(intern)) {
                LOG.info("Intern was added to DB");
                response.setHead(RESPONSE_201);
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.error("Intern was not added to DB");
        response.setHead(RESPONSE_500);
        return response;
    }

    public Response delete(Request request) {
        Response response = new Response();
        int id = request.getQuery().get("interns");
        if(dataBase.deleteIntern(id)) {
            response.setHead(RESPONSE_202);
            LOG.info("Intern was deleted");
        }
        else {
            response.setHead(RESPONSE_400);
            LOG.error("Intern was not deleted");
        }
        return response;
    }

    public Response patch(Request request) {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        Intern intern = null;
        int id = request.getQuery().get("interns");
        try {
            intern = mapper.readValue(request.getBody(), Intern.class);
            intern.setId(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dataBase.patchIntern(intern)) {
            response.setHead(RESPONSE_201);
            LOG.info("Intern was updated");
        }
        else {
            response.setHead(RESPONSE_400);
            LOG.error("Intern was not updated");
        }
        return response;
    }

    private Response send404() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        Response response = new Response();

        ((ObjectNode) rootNode).put("status", "404");
        ((ObjectNode) rootNode).put("statusText", "Not Found");
        LOG.info("404, not found");
        try {
            response.setBody(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOG.error("Didn't create response body");
        }

        response.setHead(RESPONSE_404);
        return response;
    }

    public Response routDoesNotExist(Request request) {
        return send404();
    }
}
