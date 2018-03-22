package com.server;

import com.data.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Parser {

    private static Request request;

    public static Request parseRequest(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            request = new Request();
            String queryLine;
            parseFirstLine(in.readLine(), request);
            while (!(queryLine = in.readLine()).isEmpty()) {
                System.out.println(queryLine);
                parseHead(queryLine, request);
            }
            parseBody(in);

            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return request;
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

    private static void parseHead(String requestString, Request request) {
        String[] header = requestString.split(": ");
        request.getHeader().put(header[0], header[1]);
    }

    private static void parseBody(BufferedReader in) throws IOException {
        String contentLength = request.getHeader().get("content-length");
        if(contentLength != null) {
            char[] body = new char[Integer.parseInt(contentLength.substring(1))];
            in.read(body, 0, body.length);
            request.setBody(new String(body));
        }

    }
}
