package com.server.parser;

import com.data.Request;
import com.server.RequestMethods;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
    private static final int METHOD = 0;
    private static final int PATH = 1;

    private static final Logger LOG = Logger.getLogger(Parser.class);

    private static Request request;

    public static Request parseRequest(BufferedReader in) {
        try {
            request = new Request();
            String queryLine;
            parseFirstLine(in.readLine(), request);
            while (!(queryLine = in.readLine()).isEmpty()) {
                parseHead(queryLine, request);
            }
            LOG.info("Parser has parsed head of query");
            parseBody(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    static Request parseFirstLine(String firstString, Request request) {
        String method = firstString.split(" ")[METHOD];
        request.setMethod(RequestMethods.checkMethods(method));
        String path = firstString.split(" ")[PATH];
        request.setPath(addSlashToEnding(path));
        if(path.split("/").length > 2) {
            String[] partOfPath = path.split("/");
            for(int i = 2; i < partOfPath.length; i+=2) {
                request.addQueryItem(partOfPath[i - 1], Integer.parseInt(partOfPath[i]));
            }
        }
        LOG.info("Parser has parsed first line of query");
        return request;
    }

    static String addSlashToEnding(String path) {
        if(path.charAt(path.length() - 1) != '/') {
            return path + "/";
        } else {
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
        LOG.info("Parser has parsed body");
    }
}
