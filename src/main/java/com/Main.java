package com;

import com.server.RequestMethods;
import com.server.router.Router;
import com.server.Server;
import com.server.controller.Controller;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Controller controller = new Controller();
        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/", controller::getInternWithoutId);
        router.addNewHandler(RequestMethods.GET, "/interns/:id/", controller::getInternWithId);
        router.addNewHandler(RequestMethods.GET, "/groups/", controller::getGroups);
        router.addNewHandler(RequestMethods.POST, "/interns/", controller::postNewIntern);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/", controller::options);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/:id/", controller::options);
        router.addNewHandler(RequestMethods.DELETE, "/interns/:id/", controller::delete);
        router.addNewHandler(RequestMethods.PATCH, "/interns/:id/", controller::patch);

        Server server = new Server(router);
        server.listenPort();
    }
}
