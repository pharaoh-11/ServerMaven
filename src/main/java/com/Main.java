package com;

import com.server.RequestMethods;
import com.server.Router;
import com.server.Server;
import com.server.methods.MethodsForLambda;

public class Main {

    public static void main(String[] args) {
        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/", MethodsForLambda::getInternWithoutId);
        router.addNewHandler(RequestMethods.GET, "/interns/id/", MethodsForLambda::getInternWithId);
        router.addNewHandler(RequestMethods.GET, "/groups/", MethodsForLambda::getGroups);
        router.addNewHandler(RequestMethods.POST, "/interns/", MethodsForLambda::postNewIntern);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/", MethodsForLambda::options);

        Server server = new Server(router);
        server.listenPort();
    }
}
