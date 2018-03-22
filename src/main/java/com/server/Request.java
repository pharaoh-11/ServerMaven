package com.server;

import java.util.Map;

public class Request {
    RequestMethods method;
    String path;
    String query;
    Map<String, String> header;
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

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", query='" + query + '\'' +
                ", header=" + header +
                ", body='" + body + '\'' +
                '}';
    }
}
