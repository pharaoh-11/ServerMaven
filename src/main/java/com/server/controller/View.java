package com.server.controller;

import com.entity.Entity;
import com.entity.Intern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class View {
    public static String viewBody(ArrayList<? extends Entity> body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
    }

    public static String viewBody(Intern intern) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if(!intern.isEmpty()) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(intern);
        } else {
            return null;
        }
    }
}
