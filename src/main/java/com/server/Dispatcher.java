package com.server;

import com.data.Request;
import com.handlers.ConcreteHandler;
import com.handlers.Handler;

public class Dispatcher {
    private Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public void handleRequest(Request request) {
        ConcreteHandler concreteHandler = router.findNeededHandler(request);
    }
}
