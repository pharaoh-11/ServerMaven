package com.server.controller.db;

import com.entity.Group;
import com.entity.Intern;

import java.util.ArrayList;

public interface DataBase {
    ArrayList<Intern> selectAllInterns();
    Intern selectInternsById(int id);
    boolean insertIntern(Intern intern);
    boolean deleteIntern(int id);
    boolean updateIntern(Intern intern);
    ArrayList<Group> selectGroups();
}
