package com.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private static final int PORT = 8080;
    private static final File JSON_FILE = new File("/home/intern/IdeaProjects/Server/src/main/resources/db.json");

    private ServerSocket serverSocket;
    private ObjectMapper objectMapper;
    private Request request;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
    }

    public void listenPort() {
        while(true) {


            try {
                Socket client = serverSocket.accept();

                Parser.parseRequest(client);



                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*public void close() {
        out.close();
                in.close();
                client.close();
    }*/
}




//    public static void main(String[] args) {
//        try {
//            ServerSocket ss = new ServerSocket(PORT);
//            ObjectMapper mapper = new ObjectMapper();
//
//            while (true) {
//                Socket client = ss.accept();
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                PrintWriter out = new PrintWriter(client.getOutputStream());
//
//                String line = in.readLine();
//
//                if (QueryParser.getHTTPMethod(line).equals(GET) &&
//                        QueryParser.getQuery(line).equals(INTERNS)) {
//                    JsonNode jNode = mapper.readTree(JSON_FILE);
//                    jNode = jNode.withArray(INTERNS);
//
//                    if (QueryParser.isIdInRequest(line)) {
//                        int id = QueryParser.getId(line);
//                        int i;
//                        for (i = 0; i < jNode.size(); i++) {
//                            if (Integer.parseInt(jNode.get(i).get("id").toString()) == id) {
//                                break;
//                            }
//                        }
//                        if (i == jNode.size()) {
//                            send404(out);
//                        } else {
//                            Intern intern = createIntern(jNode, i);
//                            printIntern(intern, out);
//                        }
//                    } else {
//                        Intern[] interns = createInterns(jNode);
//                        printInterns(interns, out);
//                    }
//                } else {
//                    send404(out);
//                }
//                out.close();
//                in.close();
//                client.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void send404(PrintWriter out) {
//        out.print("HTTP/1.1 404 \r\n");
//    }
//
//    private static void printIntern(Intern intern, PrintWriter out) {
//        send200(out);
//        out.print(intern);
//    }
//
//    private static void printInterns(Intern[] interns, PrintWriter out) {
//        send200(out);
//        for (Intern i : interns) {
//            out.print(i);
//        }
//    }
//
//    private static void send200(PrintWriter out) {
//        out.print("HTTP/1.1 200 \r\n");
//        out.print("Content-Type: text/plain\r\n");
//        out.print("Connection: close\r\n");
//        out.print("\r\n");
//    }
//
//    private static Intern createIntern(JsonNode jNode, int numberOfInternInArray) {
//        Intern intern = new Intern();
//        intern.setId(Integer.parseInt(jNode.get(numberOfInternInArray).get("id").toString()));
//        intern.setFirstname(jNode.get(numberOfInternInArray).get("firstName").toString());
//        intern.setLastname(jNode.get(numberOfInternInArray).get("lastName").toString());
//        intern.setEmail(jNode.get(numberOfInternInArray).get("email").toString());
//        intern.setGroupId(Integer.parseInt(jNode.get(numberOfInternInArray).get("groupId").toString()));
//        return intern;
//    }
//
//    private static Intern[] createInterns(JsonNode jNode) {
//        Intern[] interns = new Intern[jNode.size()];
//        for (int i = 0; i < jNode.size(); i++) {
//            interns[i] = createIntern(jNode, i);
//        }
//        return interns;
//    }

