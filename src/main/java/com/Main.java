package com;

import com.data.Response;
import com.entity.Intern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.RequestMethods;
import com.server.Router;
import com.server.Server;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final String RESPONSE_200 = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nConnection: close\r\n\r\n";
    private static final File JSON_FILE = new File("/home/intern/IdeaProjects/Server/src/main/resources/db.json");
    private static final String INTERNS = "interns";

    public static void main(String[] args) {
        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/",(request) -> {
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
            Intern[] interns = new Intern[jNode.size()];
            for (int i = 0; i < jNode.size(); i++) {
                interns[i] = new Intern();
                interns[i].setId(Integer.parseInt(jNode.get(i).get("id").toString()));
                interns[i].setFirstname(jNode.get(i).get("firstName").toString());
                interns[i].setLastname(jNode.get(i).get("lastName").toString());
                interns[i].setEmail(jNode.get(i).get("email").toString());
                interns[i].setGroupId(Integer.parseInt(jNode.get(i).get("groupId").toString()));
            }
            response.setHead(responseHead);
            response.setEntity(interns.toString());
            return response;});
        router.addNewHandler(RequestMethods.GET, "/interns/id/",(request) -> {
            Response response = new Response();
            String responseHead = RESPONSE_200;
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jNode = null;
            try {
                jNode = mapper.readTree(JSON_FILE);
                jNode = jNode.withArray(INTERNS);

            Intern intern = new Intern();


                String[] requestParts = request.getPath().split("/");
                int id =  Integer.parseInt(requestParts[2].split(" ")[0]);
                        int i;
                        for (i = 0; i < jNode.size(); i++) {
                            if (Integer.parseInt(jNode.get(i).get("id").toString()) == id) {
                                break;
                            }
                        }
                        if (i == jNode.size()) {
//                            send404(out);
                        } else {


                            intern.setId(Integer.parseInt(jNode.get(i).get("id").toString()));
                            intern.setFirstname(jNode.get(i).get("firstName").toString());
                            intern.setLastname(jNode.get(i).get("lastName").toString());
                            intern.setEmail(jNode.get(i).get("email").toString());
                            intern.setGroupId(Integer.parseInt(jNode.get(i).get("groupId").toString()));
                        }
            response.setHead(responseHead);
            response.setEntity(intern.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;});

        Server server = new Server(router);
        server.listenPort();
    }
}
