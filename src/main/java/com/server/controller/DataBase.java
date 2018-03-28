package com.server.controller;

import com.entity.Group;
import com.entity.Intern;

public interface DataBase {
    Intern[] getAllInterns();
    Intern getInternsById();
    void postIntern();
    void deleteIntern();
    void patchIntern();
    Group[] getGroups();
}
