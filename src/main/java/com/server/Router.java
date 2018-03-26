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
    }

    public void addNewHandler(RequestMethods method, String path, Handler handler) {
        switch (method) {
            case GET:
                handlers.get(RequestMethods.GET).add(new ConcreteHandler(method, path, handler));
                break;
            case POST:
            case PATCH:
            case DELETE:
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
//                for(ConcreteHandler handler: postHandlers) {
//                    if(handler.getPath().equals(request.getPath())){
//                        return handler;
//                    }
//                }
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
        }
        //not found concreteHandler
        throw new IllegalArgumentException();
    }

    private String makeCorrespondPathToCheck(String path) {
        return path.replaceAll("/\\d*/", "/id/");
    }
}
