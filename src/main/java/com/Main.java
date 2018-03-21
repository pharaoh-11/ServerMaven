package com;

import com.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.listenPort();
    }
}
