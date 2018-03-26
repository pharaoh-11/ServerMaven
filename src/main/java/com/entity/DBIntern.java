package com.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DBIntern {
    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String INTERNS = "interns";

    private Map<Integer, Intern> internDB;

    public DBIntern() {
        internDB = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode = null;
        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(INTERNS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intern intern = null;
        for (int i = 0; i < jNode.size(); i++) {
            try {
                intern = mapper.readValue(jNode.get(i).toString(), Intern.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            internDB.put(intern.getId(), intern);
        }
    }


}
