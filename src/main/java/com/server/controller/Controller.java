package com.server.controller;

import com.data.Request;
import com.data.Response;
import com.entity.Entity;
import com.entity.Intern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.server.controller.db.DataBase;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private static final Logger LOG = Logger.getLogger(Controller.class);

    private DataBase dataBase;
    private ResponseHead responseHead;

    public Controller(DataBase dataBase) {
        this.dataBase = dataBase;
        responseHead = new ResponseHead();
    }

    public Response getAllInterns(Request request) {
        return getResponseBody(new Response(), dataBase.getAllInterns());
    }

    public Response getGroups(Request request) {
        return getResponseBody(new Response(), dataBase.getGroups());
    }

    private Response getResponseBody(Response response, ArrayList<? extends Entity> list) {
        response.setBody(View.viewBody(list));
        response.setHead(responseHead.getResponseHead(ResponseStatus.S200));
        LOG.info("Method GET was processed");
        return response;
    }

    public Response getInternById(Request request) {
        Response response = new Response();
        int id = request.getQuery().get("interns");
        response.setBody(View.viewBody(dataBase.getInternsById(id)));

        if (response.isBodyNull()) {
            return send404();
        }
        response.setHead(responseHead.getResponseHead(ResponseStatus.S200));
        LOG.info("Method GET was processed");
        return response;
    }

    public Response options(Request request) {
        Response response = new Response();
        response.setHead(responseHead.getResponseHead(ResponseStatus.S_FOR_OPTIONS));
        LOG.info("Answer to options query method");
        return response;
    }

    public Response postNewIntern(Request request) {
        Intern intern = createInternFromRequest(request);
        if (dataBase.postIntern(intern)) {
            return successfulDataUpdate();
        } else {
            return failedDataUpdate();
        }
    }

    private Intern createInternFromRequest(Request request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(request.getBody(), Intern.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response successfulDataUpdate() {
        Response response = new Response();
        LOG.info("Data was successful updated");
        response.setHead(responseHead.getResponseHead(ResponseStatus.S201));
        return response;
    }

    private Response failedDataUpdate() {
        Response response = new Response();
        LOG.error("Data wasn't updated");
        response.setHead(responseHead.getResponseHead(ResponseStatus.S500));
        return response;
    }

    public Response patch(Request request) {
        Intern intern = createInternFromRequest(request);
        int id = request.getQuery().get("interns");
        intern.setId(id);
        if(dataBase.patchIntern(intern)) {
            return successfulDataUpdate();
        }
        else {
            return failedDataUpdate();
        }
    }

    public Response delete(Request request) {
        Response response = new Response();
        int id = request.getQuery().get("interns");
        if(dataBase.deleteIntern(id)) {
            response.setHead(responseHead.getResponseHead(ResponseStatus.S202));
            LOG.info("Intern was deleted");
        }
        else {
            response.setHead(responseHead.getResponseHead(ResponseStatus.S400));
            LOG.error("Intern was not deleted");
        }
        return response;
    }

    private Response send404() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        Response response = new Response();

        ((ObjectNode) rootNode).put("status", "404");
        ((ObjectNode) rootNode).put("statusText", "Not Found");
        LOG.info("404, not found");
        try {
            response.setBody(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOG.error("Didn't create response body");
        }

        response.setHead(responseHead.getResponseHead(ResponseStatus.S404));
        return response;
    }

    public Response routDoesNotExist(Request request) {
        return send404();
    }
}
