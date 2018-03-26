package com.server;

import com.data.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    private ServerSocket serverSocket;
    private ObjectMapper objectMapper;
    private Request request;
    private Dispatcher dispatcher;

    public Server(Router router) {
        try {
            serverSocket = new ServerSocket(PORT);
            objectMapper = new ObjectMapper();
            dispatcher = new Dispatcher(router);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenPort() {
        while(true) {
            System.out.println("Listening port 8080:");
            try (Socket client = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream())) {

                if(client == null) {
                    continue;
                }
                request = Parser.parseRequest(in);
                out.print(dispatcher.handleRequest(request));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
