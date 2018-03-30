package com.server.router;

import com.data.Request;
import com.exception.NoSuchRoutException;
import com.server.Rout;
import com.server.RequestMethods;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
    private static Router router;

    @BeforeAll
    static void setUp() {
        router = new Router();
        router.addNewRout(RequestMethods.GET, "/interns/", null);
        router.addNewRout(RequestMethods.POST, "/interns/:pId/", null);
        router.addNewRout(RequestMethods.OPTIONS, "/interns/:id/", null);
        router.addNewRout(RequestMethods.DELETE, "/interns/:number/", null);
        router.addNewRout(RequestMethods.PATCH, "/interns/:firstId/body/:secondId/", null);
    }

    @Test
    void findNeededHandler() throws NoSuchRoutException {
        Request request = new Request();
        request.setMethod(RequestMethods.GET);
        request.setPath("/interns/");
        Rout cHandler = new Rout(RequestMethods.GET, "/interns/", null);
        assertEquals(cHandler, router.findNeededRout(request));

        request = new Request();
        request.setMethod(RequestMethods.POST);
        request.setPath("/interns/2/");
        cHandler = new Rout(RequestMethods.POST, "/interns/:pId/", null);
        assertEquals(cHandler, router.findNeededRout(request));

        request = new Request();
        request.setMethod(RequestMethods.OPTIONS);
        request.setPath("/interns/13/");
        cHandler = new Rout(RequestMethods.OPTIONS, "/interns/:id/", null);
        assertEquals(cHandler, router.findNeededRout(request));

        request = new Request();
        request.setMethod(RequestMethods.DELETE);
        request.setPath("/interns/1/");
        cHandler = new Rout(RequestMethods.DELETE, "/interns/:number/", null);
        assertEquals(cHandler, router.findNeededRout(request));

        request = new Request();
        request.setMethod(RequestMethods.PATCH);
        request.setPath("/interns/2/body/22/");
        cHandler = new Rout(RequestMethods.PATCH, "/interns/:id/body/:id/", null);
        assertEquals(cHandler, router.findNeededRout(request));
    }

    @Test
    void makeCorrespondPathToCheck() {
        String answer = "/interns/:/";
        assertEquals(answer, router.makeCorrespondRequestPathToCheck("/interns/2/"));

        answer = "/groups/:/";
        assertEquals(answer, router.makeCorrespondRequestPathToCheck("/groups/12/"));
    }
}