package com.handlers;

import com.data.Request;
import com.data.Response;
import com.entity.DBIntern;
import com.server.RequestMethods;

public class ConcreteHandler {
    private RequestMethods requestMethods;
    private String path;
    private Handler handler;

    public ConcreteHandler(RequestMethods requestMethods, String path, Handler handler) {
        this.requestMethods = requestMethods;
        this.path = path.replaceAll("/:\\w*/", "/:/");
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public Response handleQuery(Request request, DBIntern dbIntern) {
        return handler.getResponse(request, dbIntern);
    }
}
