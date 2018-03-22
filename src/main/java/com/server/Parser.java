package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Parser {

    private static Request request;

    public static Request parseRequest(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream());

            request = new Request();
            String queryLine;
            LinkedList<String> queryStrings = new LinkedList<>();
            parseFirstLine(in.readLine(), request);
            while (!(queryLine = in.readLine()).isEmpty()) {
                System.out.println(queryLine);
                queryStrings.add(queryLine);
                parseHead(queryStrings, request);
            }
            System.out.println();
            parseBody(in);


            System.out.println(request);

            out.close();
            in.close();
            client.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return request;
    }


    /*public static Request parseRequest(LinkedList<String> requestStrings) {
        request = new Request();

        parseFirstLine(requestStrings.poll() ,request);
        parseHead(requestStrings, request);

        return  request;
    }*/

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

    private static void parseHead(LinkedList<String> requestStrings, Request request) {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!requestStrings.isEmpty() && (line = requestStrings.poll()).length() != 0) {
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }
        request.setHeader(headers);
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
