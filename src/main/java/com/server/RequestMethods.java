package com.server;

public enum RequestMethods {
    GET, POST, DELETE, PATCH;

    public static RequestMethods checkMethods(String method) throws IllegalArgumentException {
        for(RequestMethods rm: RequestMethods.values()){
            if(rm.name().equals(method)){
                return rm;
            }
        }
        throw new IllegalArgumentException();
    }
}


