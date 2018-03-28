package com;

import com.server.RequestMethods;
import com.server.router.Router;
import com.server.Server;
import com.server.methods.MethodsForLambda;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();

        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/", MethodsForLambda::getInternWithoutId);
        router.addNewHandler(RequestMethods.GET, "/interns/:id/", MethodsForLambda::getInternWithId);
        router.addNewHandler(RequestMethods.GET, "/groups/", MethodsForLambda::getGroups);
        router.addNewHandler(RequestMethods.POST, "/interns/", MethodsForLambda::postNewIntern);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/", MethodsForLambda::options);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/:id/", MethodsForLambda::options);
        router.addNewHandler(RequestMethods.DELETE, "/interns/:id/", MethodsForLambda::delete);
        router.addNewHandler(RequestMethods.PATCH, "/interns/:id/", MethodsForLambda::patch);

        Server server = new Server(router);
        server.listenPort();
    }
}
