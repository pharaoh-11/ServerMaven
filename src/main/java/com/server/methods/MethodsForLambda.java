package com.server.methods;

import com.data.Request;
import com.data.Response;
import com.entity.DBIntern;
import com.entity.Intern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class MethodsForLambda {
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
    private static final String RESPONSE_404 = "HTTP/1.1 404 \r\nContent-Type: application/json; charset=utf-8\r\n\r\n";

    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String GROUPS = "groups";

    public static Response getInternWithoutId(Request request, DBIntern dbIntern) {
        Response response = new Response();
        try {
            response.setBody(dbIntern.getAllInternsInJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setHead(RESPONSE_400);
            return response;
        }
        response.setHead(RESPONSE_200);
        return response;
    }

    public static Response getInternWithId(Request request, DBIntern dbIntern) {
        Response response = new Response();
        try {
            int id = request.getQuery().get("interns");
            response.setBody(dbIntern.getInternByIdInJson(id));

            if (response.isBodyNull()) {
                return send404();
            }
            response.setHead(RESPONSE_200);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response getGroups(Request request, DBIntern dbIntern) {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode;
        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(GROUPS);
            response.setBody(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jNode));
        } catch (IOException e) {
            e.printStackTrace();
            response.setHead(RESPONSE_400);
            return response;
        }
        response.setHead(RESPONSE_200);
        return response;
    }

    private static Response send404() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();

        ((ObjectNode) rootNode).put("status", "404");
        ((ObjectNode) rootNode).put("statusText", "Not Found");
        String httpResponse = null;
        try {
            httpResponse = RESPONSE_404 +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Response response = new Response();
        response.setHead(httpResponse);
        return response;
    }

    public static Response postNewIntern(Request request, DBIntern dbIntern) {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();

        Intern intern;
        String body = request.getBody();
        try {
            intern = mapper.readValue(body, Intern.class);
            intern.setId(dbIntern.getAnotherID());
            dbIntern.addNewIntern(intern);
        } catch (IOException e) {
            e.printStackTrace();
            response.setHead(RESPONSE_400);
            return response;
        }
        response.setHead(RESPONSE_201);
        return response;
    }

    public static Response options(Request request, DBIntern dbIntern) {
        Response response = new Response();
        response.setHead(RESPONSE_FOR_OPTIONS);
        return response;
    }

    public static Response delete(Request request, DBIntern dbIntern) {
        Response response = new Response();
        int id = Integer.parseInt(request.getBody());
        if(dbIntern.deleteIntern(id)) {
            response.setHead(RESPONSE_202);
        }
        else {
            response.setHead(RESPONSE_400);
        }
        return response;
    }

    public static Response patch(Request request, DBIntern dbIntern) {
        Response response = new Response();
        int id = Integer.parseInt(request.getBody());
        if(dbIntern.patch(id, request.getBody())) {
            response.setHead(RESPONSE_201);
        }
        else {
            response.setHead(RESPONSE_400);
        }
        return response;
    }
}
