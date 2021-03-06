package com.server;

import com.data.Request;
import com.data.Response;
import com.handlers.Handler;

import java.util.Objects;

public class Rout {
    private RequestMethods requestMethods;
    private String path;
    private Handler handler;

    public Rout(RequestMethods requestMethods, String path, Handler handler) {
        this.requestMethods = requestMethods;
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public Response handleQuery(Request request) {
        return handler.getResponse(request);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rout that = (Rout) o;
        return requestMethods == that.requestMethods &&
                Objects.equals(path, that.path) &&
                Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {

        return Objects.hash(requestMethods, path, handler);
    }
}
