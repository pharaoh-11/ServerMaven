package com.server;

import com.data.Request;
import com.data.Response;
import com.entity.DBIntern;
import com.handlers.ConcreteHandler;
import com.server.router.Router;

public class Dispatcher {
    private Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public Response handleRequest(Request request, DBIntern dbIntern) {
        ConcreteHandler concreteHandler = router.findNeededHandler(request);
        Response response = concreteHandler.handleQuery(request, dbIntern);
        return response;
    }
}
