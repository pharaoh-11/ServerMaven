package com.server.controller.db;

import com.entity.Group;
import com.entity.Intern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class MemoryDataBase implements DataBase {
    private static final File JSON_FILE = new File("./src/main/resources/db.json");
    private static final String INTERNS = "interns";
    private static final String GROUPS = "groups";

    private int id;
    private ArrayList<Intern> internList;
    private ArrayList<Group> groupList;

    public MemoryDataBase() {
        internList = new ArrayList<>();
        groupList = new ArrayList<>();

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


            jNode = mapper.readTree(JSON_FILE);
            jNode = jNode.withArray(GROUPS);
            Group group;
            for (int i = 0; i < jNode.size(); i++) {
                group = mapper.readValue(jNode.get(i).toString(), Group.class);
                groupList.add(group);
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

    @Override
    public ArrayList<Intern> getAllInterns() {
        return internList;
    }

    @Override
    public Intern getInternsById(int id) {
        for(Intern intern : internList) {
            if(intern.getId() == id) {
                return intern;
            }
        }
        return null;
    }

    @Override
    public void postIntern(Intern intern) {
        intern.setId(getAnotherID());
        internList.add(intern);
    }

    @Override
    public boolean deleteIntern(int id) {
        for(Intern intern : internList) {
            if(intern.getId() == id) {
                internList.remove(intern);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean patchIntern(Intern intern) {
        Intern internInList;
        for(ListIterator<Intern> iterator = internList.listIterator(); iterator.hasNext(); ) {
            internInList = iterator.next();
            if(internInList.getId() == intern.getId()) {
                iterator.set(intern);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Group> getGroups() {
        return groupList;
    }
}
