package com.server;

import com.data.Request;
import com.handlers.ConcreteHandler;
import com.handlers.Handler;

import java.util.LinkedList;

public class Router {
    LinkedList<ConcreteHandler> getHandlers;
    LinkedList<ConcreteHandler> putHandlers;
    LinkedList<ConcreteHandler> postHandlers;
    LinkedList<ConcreteHandler> patchHandlers;
    LinkedList<ConcreteHandler> deleteHandlers;
    LinkedList<ConcreteHandler> headHandlers;

    public Router() {
        this.getHandlers = new LinkedList<>();
        this.putHandlers = new LinkedList<>();
        this.postHandlers = new LinkedList<>();
        this.patchHandlers = new LinkedList<>();
        this.deleteHandlers = new LinkedList<>();
        this.headHandlers = new LinkedList<>();
    }

    public void addNewHandler(RequestMethods method, String path, Handler handler) {
        switch (method) {
            case GET:
                getHandlers.add(new ConcreteHandler(method, path, handler));
                break;
            case PUT:
            case POST:
            case PATCH:
            case DELETE:
            case HEAD:
        }
    }

    public ConcreteHandler findNeededHandler(Request request) {


        switch(request.getMethod()) {
            case GET:
                for(ConcreteHandler handler: getHandlers) {
                    if(handler.getPath().equals(makeCorrespondPathToCheck(request.getPath()))) {
                        return handler;
                    }
                }
                break;
            case PUT:
                for(ConcreteHandler handler: putHandlers) {
                    if(handler.getPath().equals(request.getPath())){
                        return handler;
                    }
                }
                break;
            case POST:
                for(ConcreteHandler handler: postHandlers) {
                    if(handler.getPath().equals(request.getPath())){
                        return handler;
                    }
                }
                break;
            case PATCH:
                for(ConcreteHandler handler: patchHandlers) {
                    if(handler.getPath().equals(request.getPath())){
                        return handler;
                    }
                }
                break;
            case DELETE:for(ConcreteHandler handler: deleteHandlers) {
                if(handler.getPath().equals(request.getPath())){
                    return handler;
                }
            }

                break;
            case HEAD:
                for(ConcreteHandler handler: headHandlers) {
                    if(handler.getPath().equals(request.getPath())){
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
