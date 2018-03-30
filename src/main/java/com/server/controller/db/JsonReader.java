package com.server.controller.db;

import com.entity.Entity;
import com.entity.Group;
import com.entity.Intern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader {

    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String INTERNS = "interns";
    private static final String GROUPS = "groups";


    public ArrayList<Intern> getInternList() {
        ArrayList<Intern> internList = new ArrayList<>();
       return getList(INTERNS, internList, Intern.class);
    }
    public ArrayList<Group> getGroupList() {
        ArrayList<Group> groupList = new ArrayList<>();
        return getList(GROUPS, groupList, Group.class);
    }

    private <T extends Entity> ArrayList<T> getList(String item, ArrayList<T> list, Class<T> className) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode;

        try {
            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(item);
            for (int i = 0; i < jNode.size(); i++) {
                list.add(mapper.readValue(jNode.get(i).toString(), className));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
