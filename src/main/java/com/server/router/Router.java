package com.server.router;

import com.data.Request;
import com.exception.NoSuchHandlerException;
import com.handlers.ConcreteHandler;
import com.handlers.Handler;
import com.server.RequestMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    Map<RequestMethods, ArrayList<ConcreteHandler>> handlers;

    public Router() {
        handlers = new HashMap<>();
        handlers.put(RequestMethods.GET, new ArrayList<>());
        handlers.put(RequestMethods.POST, new ArrayList<>());
        handlers.put(RequestMethods.PATCH, new ArrayList<>());
        handlers.put(RequestMethods.DELETE, new ArrayList<>());
        handlers.put(RequestMethods.OPTIONS, new ArrayList<>());
    }

    public void addNewHandler(RequestMethods method, String path, Handler handler) {
        handlers.get(method).add(new ConcreteHandler(method, path, handler));
    }

    public ConcreteHandler findNeededHandler(Request request) throws NoSuchHandlerException {
        for(ConcreteHandler handler: handlers.get(request.getMethod())) {
            if(handler.getPath().equals(makeCorrespondPathToCheck(request.getPath()))) {
                return handler;
            }
        }
        //not found concreteHandler
        throw new NoSuchHandlerException();
    }

    String makeCorrespondPathToCheck(String path) {
        return path.replaceAll("/\\d*/", "/:/");
    }
}
