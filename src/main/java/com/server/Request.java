package com.server;

public class Request {
    RequestMethods method;
    String path;
    String query;
    String header;
    String body;

    public void setMethod(RequestMethods method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
