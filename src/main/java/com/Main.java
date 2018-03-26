package com;

import com.server.RequestMethods;
import com.server.Router;
import com.server.Server;
import com.server.methods.MethodsForLambda;

public class Main {

    public static void main(String[] args) {
        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/", MethodsForLambda::GetInternWithoutId);
        router.addNewHandler(RequestMethods.GET, "/interns/id/", MethodsForLambda::GetInternWithId);
        router.addNewHandler(RequestMethods.GET, "/groups/", MethodsForLambda::GetGroups);

        Server server = new Server(router);
        server.listenPort();
    }
}
