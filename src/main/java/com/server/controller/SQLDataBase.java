package com.server.controller;

import com.entity.Group;
import com.entity.Intern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDataBase implements DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "chateau_x86";

    private Connection connection;

    public void createConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Intern[] getAllInterns() {
        return new Intern[0];
    }

    @Override
    public Intern getInternsById() {
        return null;
    }

    @Override
    public void postIntern() {

    }

    @Override
    public void deleteIntern() {

    }

    @Override
    public void patchIntern() {

    }

    @Override
    public Group[] getGroups() {
        return new Group[0];
    }
}
