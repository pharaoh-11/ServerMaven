package com.server.controller.db;

import com.entity.Group;
import com.entity.Intern;

import java.util.ArrayList;

public interface DataBase {
    ArrayList<Intern> getAllInterns();
    Intern getInternsById(int id);
    void postIntern(Intern intern);
    boolean deleteIntern(int id);
    boolean patchIntern(Intern intern);
    ArrayList<Group> getGroups();
}
