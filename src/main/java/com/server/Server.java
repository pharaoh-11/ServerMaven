package com.server;

import com.data.Request;
import com.server.controller.MemoryDataBase;
import com.server.parser.Parser;
import com.server.router.Router;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    private static final Logger LOG = Logger.getLogger(Server.class);

    private ServerSocket serverSocket;
    private Request request;
    private Dispatcher dispatcher;
    private MemoryDataBase memoryDataBase;

    public Server(Router router) {
        try {
            serverSocket = new ServerSocket(PORT);
            dispatcher = new Dispatcher(router);
            memoryDataBase = new MemoryDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenPort() {
        System.out.println("Listening port 8080:");
        while(true) {
            try (Socket client = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream())) {

                LOG.info("Server has accepted query.");

                request = Parser.parseRequest(in);
                out.print(dispatcher.handleRequest(request));
            } catch (IOException e) {
                LOG.error("Happened something bad");
                e.printStackTrace();
            }
        }
    }
}
