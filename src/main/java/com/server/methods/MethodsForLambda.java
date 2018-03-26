package com.server.methods;

import com.data.Request;
import com.data.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MethodsForLambda {
    private static final String RESPONSE_200 = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String INTERNS = "interns";
    private static final String GROUPS = "groups";

    public static Response GetInternWithoutId(Request request) {
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

    public static Response GetInternWithId(Request request) {
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
//                            send404(out);
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

    public static Response GetGroups(Request request) {
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
}
