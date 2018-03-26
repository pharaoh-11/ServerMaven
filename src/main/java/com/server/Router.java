package com.server;

import com.data.Request;
import com.handlers.ConcreteHandler;
import com.handlers.Handler;
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
        switch (method) {
            case GET:
                handlers.get(RequestMethods.GET).add(new ConcreteHandler(method, path, handler));
                break;
            case POST:
                handlers.get(RequestMethods.POST).add(new ConcreteHandler(method, path, handler));
                break;
            case PATCH:
                break;
            case DELETE:
                break;
            case OPTIONS:
                handlers.get(RequestMethods.OPTIONS).add(new ConcreteHandler(method, path, handler));
        }
    }

    public ConcreteHandler findNeededHandler(Request request) {
        switch(request.getMethod()) {
            case GET:
                for(ConcreteHandler handler: handlers.get(RequestMethods.GET)) {
                    if(handler.getPath().equals(makeCorrespondPathToCheck(request.getPath()))) {
                        return handler;
                    }
                }
                break;
            case POST:
                for(ConcreteHandler handler: handlers.get(RequestMethods.POST)) {
                    if(handler.getPath().equals(makeCorrespondPathToCheck(request.getPath()))){
                        return handler;
                    }
                }
                break;
            case PATCH:
//                for(ConcreteHandler handler: patchHandlers) {
//                    if(handler.getPath().equals(request.getPath())){
//                        return handler;
//                    }
//                }
                break;
            case DELETE:
//                for(ConcreteHandler handler: deleteHandlers) {
//                    if (handler.getPath().equals(request.getPath())) {
//                        return handler;
//                    }
//            }

                break;
            case OPTIONS:
                for(ConcreteHandler handler: handlers.get(RequestMethods.OPTIONS)) {
                    if(handler.getPath().equals(makeCorrespondPathToCheck(request.getPath()))){
                        return handler;
                    }
                }
        }
        //not found concreteHandler
        throw new IllegalArgumentException();
    }

    private String makeCorrespondPathToCheck(String path) {
        return path.replaceAll("/\\d*/", "/id/");
    }
}
