package com.data;

import com.server.RequestMethods;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private RequestMethods method;
    private String path;
    private String query;
    private Map<String, String> header;
    private String body;

    public Request() {
        header = new HashMap<>();
    }

    public void setMethod(RequestMethods method) {
        this.method = method;
    }

    public RequestMethods getMethod() {
        return method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
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
