package com.server.parser;

import com.data.Request;
import com.server.RequestMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Parser {

    private static Request request;

    public static Request parseRequest(BufferedReader in) {
        try {
            request = new Request();
            String queryLine;
            parseFirstLine(in.readLine(), request);
            while (!(queryLine = in.readLine()).isEmpty()) {
                parseHead(queryLine, request);
            }
            parseBody(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(request);
        return request;
    }

    static Request parseFirstLine(String firstString, Request request) {
        String method = firstString.split(" ")[0];
        request.setMethod(RequestMethods.checkMethods(method));
        String path = firstString.split(" ")[1];
        request.setPath(addSlashToEnding(path));
        if(path.split("/").length > 2) {
            String[] partOfPath = path.split("/");
            for(int i = 2; i < partOfPath.length; i+=2) {
                request.addQueryItem(partOfPath[i - 1], Integer.parseInt(partOfPath[i]));
            }
        }

//        if(path.indexOf('?') < 0) {
//            request.setPath(addSlashToEnding(path));
//        } else {
//            String [] pathAndQuery = path.split("\\?");
//            request.setPath(addSlashToEnding(pathAndQuery[0]));
//            request.setQuery(pathAndQuery[1]);
//        }
        return request;
    }

    static String addSlashToEnding(String path) {
        if(path.charAt(path.length() - 1) != '/') {
            return path + "/";
        }
        else {
            return path;
        }
    }

    static Request parseHead(String requestString, Request request) {
        String[] header = requestString.split(": ");
        request.getHeader().put(header[0], header[1]);
        return request;
    }

    static void parseBody(BufferedReader in) throws IOException {
        String contentLength = request.getHeader().get("Content-Length");
        if(contentLength != null) {
            char[] body = new char[Integer.parseInt(contentLength)];
            in.read(body, 0, body.length);
            request.setBody(new String(body));
        }
    }
}
