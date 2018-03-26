package com.server;

import com.data.Request;
import java.io.BufferedReader;
import java.io.IOException;

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
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(request);
        return request;
    }

    private static void parseFirstLine(String firstString, Request request) {
        String method = firstString.split(" ")[0];
        request.setMethod(RequestMethods.checkMethods(method));

        String path = firstString.split(" ")[1];
        if(path.indexOf('?') < 0) {
            request.setPath(addSlashToEnding(path));
        } else {
            String [] pathAndQuery = path.split("\\?");
            request.setPath(addSlashToEnding(pathAndQuery[0]));
            request.setQuery(pathAndQuery[1]);
        }
    }

    private static String addSlashToEnding(String path) {
        if(path.charAt(path.length() - 1) != '/') {
            return path + "/";
        }
        else {
            return path;
        }
    }

    private static void parseHead(String requestString, Request request) {
        String[] header = requestString.split(": ");
        request.getHeader().put(header[0], header[1]);
    }

    private static void parseBody(BufferedReader in) throws IOException {
        String contentLength = request.getHeader().get("Content-Length");
        if(contentLength != null) {
            char[] body = new char[Integer.parseInt(contentLength)];
            in.read(body, 0, body.length);
            System.out.println(new String(body));
            request.setBody(new String(body));
        }
    }
}
