package com;

import com.server.RequestMethods;
import com.server.controller.Controller;
import com.server.controller.db.MemoryDataBase;
import com.server.controller.db.SQLDataBase;
import com.server.router.Router;
import com.server.Server;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Controller controller = new Controller(new SQLDataBase());
        Router router = new Router();

        router.addNewRout(RequestMethods.GET, "/interns/", controller::getAllInterns);
        router.addNewRout(RequestMethods.GET, "/interns/:id/", controller::getInternById);
        router.addNewRout(RequestMethods.GET, "/groups/", controller::getGroups);
        router.addNewRout(RequestMethods.POST, "/interns/", controller::postNewIntern);
        router.addNewRout(RequestMethods.OPTIONS, "/interns/", controller::options);
        router.addNewRout(RequestMethods.OPTIONS, "/interns/:id/", controller::options);
        router.addNewRout(RequestMethods.DELETE, "/interns/:id/", controller::delete);
        router.addNewRout(RequestMethods.PATCH, "/interns/:id/", controller::patch);
        router.addNewRout(null, null, controller::defaultRout);

        Server server = new Server(router);
        server.listenPort();
    }
}
