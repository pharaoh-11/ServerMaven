package com.server;

import com.data.Request;
import com.data.Response;
import com.handlers.ConcreteHandler;

public class Dispatcher {
    private Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public Response handleRequest(Request request) {
        ConcreteHandler concreteHandler = router.findNeededHandler(request);
        Response response = concreteHandler.handleQuery();
        return response;
    }
}
