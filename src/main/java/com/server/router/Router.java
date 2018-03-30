package com.server.router;

import com.data.Request;
import com.server.Rout;
import com.handlers.Handler;
import com.server.RequestMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    Map<RequestMethods, ArrayList<Rout>> handlers;
    Rout defaultRout;

    public Router() {
        handlers = new HashMap<>();
        handlers.put(RequestMethods.GET, new ArrayList<>());
        handlers.put(RequestMethods.POST, new ArrayList<>());
        handlers.put(RequestMethods.PATCH, new ArrayList<>());
        handlers.put(RequestMethods.DELETE, new ArrayList<>());
        handlers.put(RequestMethods.OPTIONS, new ArrayList<>());
    }

    public void addNewRout(RequestMethods method, String path, Handler handler) {
        if(method != null) {
            handlers.get(method).add(new Rout(method, path, handler));
        } else {
            defaultRout = new Rout(method, path, handler);
        }
    }

    public Rout findNeededRout(Request request) {
        for(Rout rout: handlers.get(request.getMethod())) {
            if(makeCorrespondRoutPathToCheck(rout.getPath()).equals(makeCorrespondRequestPathToCheck(request.getPath()))) {
                return rout;
            }
        }
        return defaultRout;
    }

    String makeCorrespondRequestPathToCheck(String path) {
        return path.replaceAll("/\\d*/", "/:/");
    }

    private String makeCorrespondRoutPathToCheck(String path) {
        return path.replaceAll("/:\\w*/", "/:/");
    }
}
