package com;

import com.server.RequestMethods;
import com.server.Router;
import com.server.Server;

public class Main {
    public static void main(String[] args) {
        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "interns/",() -> {return null;});

        Server server = new Server(router);
        server.listenPort();
    }
}
