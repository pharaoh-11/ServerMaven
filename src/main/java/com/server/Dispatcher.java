package com.server;

import com.data.Request;
import com.data.Response;
import com.server.router.Router;
import org.apache.log4j.Logger;

public class Dispatcher {
    private static final Logger LOG = Logger.getLogger(Dispatcher.class);

    private Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public Response handleRequest(Request request) {
        Rout rout;
        Response response;
        rout = router.findNeededRout(request);
        LOG.info("Dispatcher received correspond handler");
        response = rout.handleQuery(request);
        return response;
    }
}
