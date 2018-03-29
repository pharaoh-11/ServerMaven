package com;

import com.server.RequestMethods;
import com.server.controller.ControllerNew;
import com.server.router.Router;
import com.server.Server;
import com.server.controller.Controller;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Controller controller = new Controller();
        ControllerNew controllerNew = new ControllerNew();
        Router router = new Router();
        router.addNewHandler(RequestMethods.GET, "/interns/", controllerNew::getAllInterns);
        router.addNewHandler(RequestMethods.GET, "/interns/:id/", controllerNew::getInternById);
        router.addNewHandler(RequestMethods.GET, "/groups/", controllerNew::getGroups);
        router.addNewHandler(RequestMethods.POST, "/interns/", controllerNew::postNewIntern);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/", controllerNew::options);
        router.addNewHandler(RequestMethods.OPTIONS, "/interns/:id/", controllerNew::options);
        router.addNewHandler(RequestMethods.DELETE, "/interns/:id/", controllerNew::delete);
        router.addNewHandler(RequestMethods.PATCH, "/interns/:id/", controllerNew::patch);

        Server server = new Server(router);
        server.listenPort();
    }
}
