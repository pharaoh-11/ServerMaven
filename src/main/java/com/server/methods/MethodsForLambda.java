package com.server.methods;

import com.data.Request;
import com.data.Response;
import com.entity.Intern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class MethodsForLambda {
    private static final String RESPONSE_200 = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_201 = "HTTP/1.1 201 OK\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_FOR_OPTIONS = "HTTP/1.0 200 OK\n" +
            "Content-Type: text/html; charset=utf-8\n" +
            "Allow: GET, OPTIONS, POST";
    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String INTERNS = "interns";
    private static final String GROUPS = "groups";

    public static Response getInternWithoutId(Request request) {
        Response response = new Response();
        String responseHead = RESPONSE_200;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode = null;
        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(INTERNS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHead(responseHead);
        response.setEntity(jNode);
        return response;
    }

    public static Response getInternWithId(Request request) {
        Response response = new Response();
        String responseHead = RESPONSE_200;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode;
        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(INTERNS);

            String[] requestParts = request.getPath().split("/");
            int id = Integer.parseInt(requestParts[2].split(" ")[0]);
            int i;
            for (i = 0; i < jNode.size(); i++) {
                if (Integer.parseInt(jNode.get(i).get("id").toString()) == id) {
                    break;
                }
            }
            if (i == jNode.size()) {
                return send404();
            } else {
                response.setEntity(jNode.get(i));
            }
            response.setHead(responseHead);
            response.setEntity(jNode.get(i));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response getGroups(Request request) {
        Response response = new Response();
        String responseHead = RESPONSE_200;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode = null;
        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(GROUPS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHead(responseHead);
        response.setEntity(jNode);
        return response;
    }

    private static Response send404() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();

        ((ObjectNode) rootNode).put("status", "404");
        ((ObjectNode) rootNode).put("statusText", "Not Found");
        String httpResponse = null;
        try {
            httpResponse = "HTTP/1.1 400 \r\n" +
                    "Content-Type: application/json; charset=utf-8\r\n" +
                    "\r\n" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Response response  = new Response();
        response.setHead(httpResponse);
        return response;
    }

    public static Response postNewIntern(Request request) {
        Response response = new Response();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode;


        String responseHead = RESPONSE_201;//if data was written

        System.out.println(request);
        Intern intern = new Intern();
        String acceptEncoding = request.getBody();
        System.out.println("Bottom body");
        System.out.println(acceptEncoding);
        String[] fields = acceptEncoding.split(" ");
        System.out.println(fields);



//        try {
//            jNode = mapper.readTree(JSON_FILE);
//            jNode = jNode.withArray(INTERNS);
//
//            String[] requestParts = request.getPath().split("/");
//            int id = Integer.parseInt(requestParts[2].split(" ")[0]);
//            int i;
//            for (i = 0; i < jNode.size(); i++) {
//                if (Integer.parseInt(jNode.get(i).get("id").toString()) == id) {
//                    break;
//                }
//            }
//            if (i == jNode.size()) {
//                return send404();
//            } else {
//                response.setEntity(jNode.get(i));
//            }
//            response.setHead(responseHead);
//            response.setEntity(jNode.get(i));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return response;
    }

    public static Response options(Request request) {
        Response response = new Response();
        response.setHead(RESPONSE_FOR_OPTIONS);
        return response;
    }
}
