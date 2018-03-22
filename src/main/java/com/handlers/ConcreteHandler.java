package com.handlers;

import com.server.RequestMethods;

public class ConcreteHandler {
    private RequestMethods requestMethods;
    private String path;
    private Handler handler;

    public ConcreteHandler(RequestMethods requestMethods, String path, Handler handler) {
        this.requestMethods = requestMethods;
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }
}
