package com.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DBIntern {
    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String INTERNS = "interns";

    private int id = 0;

    private ArrayList<Intern> internList;

    public ArrayList<Intern> getInternDB() {
        return internList;
    }

    public DBIntern() {
        internList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode;
        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(INTERNS);
            Intern intern;
            for (int i = 0; i < jNode.size(); i++) {
                intern = mapper.readValue(jNode.get(i).toString(), Intern.class);
                internList.add(intern);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        calculateId();
    }

    private void calculateId() {
        for(Intern intern : internList) {
            if(intern.getId() >= id) {
                id = intern.getId() + 1;
            }
        }
    }

    public int getAnotherID() {
        return this.id++;
    }

    public String getAllInternsInJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(internList);
    }

    public String getInternByIdInJson(int id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        for(Intern intern : internList) {
            if(intern.getId() == id) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(intern);
            }
        }
        return null;
    }

    public void addNewIntern(Intern intern) {
        internList.add(intern);
    }
//check iterator
    public boolean deleteIntern(int id) {
        for(Intern intern : internList) {
            if(intern.getId() == id) {
                internList.remove(intern);
                return true;
            }
        }
        return false;
    }
//check to the id
    public boolean patch(int id, String internJson) {
        ObjectMapper mapper = new ObjectMapper();
        for(Intern intern : internList) {
            if(intern.getId() == id) {
                try {
                    intern = mapper.readValue(internJson, Intern.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
