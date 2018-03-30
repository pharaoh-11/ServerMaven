package com.server.controller.db;

import com.entity.Group;
import com.entity.Intern;
import java.util.ArrayList;
import java.util.ListIterator;

public class MemoryDataBase implements DataBase {
    private int id;
    private ArrayList<Intern> internList;
    private ArrayList<Group> groupList;

    public MemoryDataBase() {
        JsonReader jsonReader = new JsonReader();
        internList = jsonReader.getInternList();
        groupList = jsonReader.getGroupList();

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
    public boolean postIntern(Intern intern) {
        intern.setId(getAnotherID());
        internList.add(intern);
        return true;
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
