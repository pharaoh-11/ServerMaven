package com.server.router;

import com.data.Request;
import com.exception.NoSuchHandlerException;
import com.handlers.ConcreteHandler;
import com.server.RequestMethods;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
    private static Router router;

    @BeforeAll
    static void setUp() {
        router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/", null);
        router.addNewHandler(RequestMethods.POST, "/interns/:pId/", null);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/:id/", null);
        router.addNewHandler(RequestMethods.DELETE, "/interns/:number/", null);
        router.addNewHandler(RequestMethods.PATCH, "/interns/:firstId/body/:secondId/", null);
    }

    @Test
    void findNeededHandler() throws NoSuchHandlerException {
        Request request = new Request();
        request.setMethod(RequestMethods.GET);
        request.setPath("/interns/");
        ConcreteHandler cHandler = new ConcreteHandler(RequestMethods.GET, "/interns/", null);
        assertEquals(cHandler, router.findNeededHandler(request));

        request = new Request();
        request.setMethod(RequestMethods.POST);
        request.setPath("/interns/2/");
        cHandler = new ConcreteHandler(RequestMethods.POST, "/interns/:pId/", null);
        assertEquals(cHandler, router.findNeededHandler(request));

        request = new Request();
        request.setMethod(RequestMethods.OPTIONS);
        request.setPath("/interns/13/");
        cHandler = new ConcreteHandler(RequestMethods.OPTIONS, "/interns/:id/", null);
        assertEquals(cHandler, router.findNeededHandler(request));

        request = new Request();
        request.setMethod(RequestMethods.DELETE);
        request.setPath("/interns/1/");
        cHandler = new ConcreteHandler(RequestMethods.DELETE, "/interns/:number/", null);
        assertEquals(cHandler, router.findNeededHandler(request));

        request = new Request();
        request.setMethod(RequestMethods.PATCH);
        request.setPath("/interns/2/body/22/");
        cHandler = new ConcreteHandler(RequestMethods.PATCH, "/interns/:id/body/:id/", null);
        assertEquals(cHandler, router.findNeededHandler(request));
    }

    @Test
    void makeCorrespondPathToCheck() {
        String answer = "/interns/:/";
        assertEquals(answer, router.makeCorrespondPathToCheck("/interns/2/"));

        answer = "/groups/:/";
        assertEquals(answer, router.makeCorrespondPathToCheck("/groups/12/"));
    }
}