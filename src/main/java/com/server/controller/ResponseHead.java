package com.server.controller;

import java.util.HashMap;
import java.util.Map;

public class ResponseHead {

    private static final String RESPONSE_200 = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_201 = "HTTP/1.1 201 Created\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_202 = "HTTP/1.1 202 No Content\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_FOR_OPTIONS = "HTTP/1.1 204 No Content\r\n"+
            "Access-Control-Allow-Credentials: true\r\n"+
            "Access-Control-Allow-Headers: Content-Type\r\n"+
            "Access-Control-Allow-Methods: GET, HEAD, PUT, PATCH, POST, DELETE, OPTIONS\r\n" +
            "Access-Control-Allow-Origin: * \r\nConnection: keep-alive\r\n" +
            "Vary: Origin, Access-Control-Request-Headers\r\n\r\n";
    private static final String RESPONSE_400 = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";
    private static final String RESPONSE_404 = "HTTP/1.1 404 Not Found\r\nContent-Type: application/json; charset=utf-8\r\n\r\n";
    private static final String RESPONSE_500 = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/plain\r\nAccess-Control-Allow-Origin: *\r\nConnection: close\r\n\r\n";

    private Map<ResponseStatus, String> responseHeads;

    public ResponseHead() {
        responseHeads = new HashMap<>();
        responseHeads.put(ResponseStatus.S200, RESPONSE_200);
        responseHeads.put(ResponseStatus.S201, RESPONSE_201);
        responseHeads.put(ResponseStatus.S202, RESPONSE_202);
        responseHeads.put(ResponseStatus.S_FOR_OPTIONS, RESPONSE_FOR_OPTIONS);
        responseHeads.put(ResponseStatus.S400, RESPONSE_400);
        responseHeads.put(ResponseStatus.S404, RESPONSE_404);
        responseHeads.put(ResponseStatus.S500, RESPONSE_500);
    }

    public String getResponseHead(ResponseStatus responseStatus) {
        return responseHeads.get(responseStatus);
    }

}
