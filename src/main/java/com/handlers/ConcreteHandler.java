package com.handlers;

import com.data.Request;
import com.data.Response;
import com.entity.DBIntern;
import com.server.RequestMethods;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcreteHandler that = (ConcreteHandler) o;
        return requestMethods == that.requestMethods &&
                Objects.equals(path, that.path) &&
                Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {

        return Objects.hash(requestMethods, path, handler);
    }
}
