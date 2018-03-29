package com.server;

public enum RequestMethods {
    GET, POST, DELETE, PATCH, OPTIONS;

    public static RequestMethods checkMethods(String method) {
        for(RequestMethods rm: RequestMethods.values()){
            if(rm.name().equals(method)){
                return rm;
            }
        }
        return null;
    }
}


