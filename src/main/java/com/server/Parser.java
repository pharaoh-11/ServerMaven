package com.server;

import java.util.LinkedList;

public class Parser {



    public static Request parseRequest(LinkedList<String> requestStrings) {
        Request request = new Request();

        parseFirstLine(requestStrings.poll() ,request);

//String to head
        return  request;
    }

    private static void parseFirstLine(String firstString, Request request) {
        String method = firstString.split(" ")[0];
        request.setMethod(RequestMethods.checkMethods(method));

        String path = firstString.split(" ")[1];
        if(path.indexOf('?') < 0) {
            request.setPath(path);
        } else {
            String [] pathAndQuery = path.split("\\?");
            request.setPath(pathAndQuery[0]);
            request.setQuery(pathAndQuery[1]);
        }
    }
}
