package com.server.controller;

import com.entity.Entity;
import com.entity.Intern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class View {
    public static String viewBody(ArrayList<? extends Entity> body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String viewBody(Intern intern) {
        ObjectMapper mapper = new ObjectMapper();
        if(!intern.isEmpty()) {
            try {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(intern);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
