package com.server;

import com.data.Request;
import com.entity.DBIntern;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    private ServerSocket serverSocket;
    private Request request;
    private Dispatcher dispatcher;
    private DBIntern dBIntern;

    public Server(Router router) {
        try {
            serverSocket = new ServerSocket(PORT);
            dispatcher = new Dispatcher(router);
            dBIntern = new DBIntern();
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
