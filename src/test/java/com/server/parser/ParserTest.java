package com.server.parser;

import com.data.Request;
import com.server.RequestMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private static Request request;

    @BeforeEach
    void Before() {
        request = new Request();
    }

    @Test
    void parseFirstLine() {
        request.setMethod(RequestMethods.GET);
        request.setPath("interns/");
        assertEquals(request, Parser.parseFirstLine("GET interns HTTP/1.1", new Request()));

        request.setMethod(RequestMethods.POST);
        request.setPath("interns/12/");
        assertEquals(request, Parser.parseFirstLine("POST interns/12/ HTTP/1.1", new Request()));

        request.setMethod(RequestMethods.POST);
        request.setPath("interns/12/qwer/");
        assertEquals(request, Parser.parseFirstLine("POST interns/12/qwer HTTP/1.1", new Request()));
    }

    @Test
    void addSlashToEnding() {
        assertEquals("interns/",Parser.addSlashToEnding("interns"));
        assertEquals("interns/1/",Parser.addSlashToEnding("interns/1"));
        assertEquals("interns/123/qwer/",Parser.addSlashToEnding("interns/123/qwer"));
        assertEquals("interns/123/qwer/31/",Parser.addSlashToEnding("interns/123/qwer/31"));
    }

    @Test
    void parseHead() {
        request.getHeader().put("Content-Type","application/json");
        assertEquals(request, Parser.parseHead("Content-Type: application/json", new Request()));

        Request tempRequest = request;
        request.getHeader().put("Access-Control-Allow-Origin","*");
        assertEquals(request, Parser.parseHead("Content-Type: application/json", tempRequest));
    }
}