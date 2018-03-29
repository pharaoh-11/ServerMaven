package com.server.controller.db;

import com.entity.Group;
import com.entity.Intern;

import java.sql.*;
import java.util.ArrayList;

public class SQLDataBase implements DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "chateau_x86";

    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String GROUP_ID = "groupId";
    private static final String NAME = "name";
    private static final String PERIOD_START = "periodStart";
    private static final String PERIOD_FINISH = "periodFinish";

    private static final String SELECT_ALL_INTERNS = "SELECT * FROM interns";
    private static final String SELECT_INTERN_BY_ID = "SELECT * FROM interns WHERE id = ?";
    private static final String INSERT_INTERN = "INSERT INTO interns VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_INTERN = "DELETE FROM interns WHERE id = ?";
    private static final String UPDATE_INTERN = "UPDATE interns SET firstName = ?, lastName = ?, email = ?, groupId = ? WHERE id = ?";
    private static final String SELECT_ALL_GROUPS = "SELECT * FROM groups";

    private Connection connection;

    public SQLDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Intern> getAllInterns() {
        ArrayList<Intern> interns = new ArrayList<>();
        Intern intern;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INTERNS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                intern = new Intern();
                intern.setId(resultSet.getInt(ID));
                intern.setFirstName(resultSet.getString(FIRST_NAME));
                intern.setLastName(resultSet.getString(LAST_NAME));
                intern.setEmail(resultSet.getString(EMAIL));
                intern.setGroupId(resultSet.getInt(GROUP_ID));
                interns.add(intern);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return interns;
    }

    @Override
    public Intern getInternsById(int id) {
        Intern intern = new Intern();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INTERN_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                intern.setId(resultSet.getInt(ID));
                intern.setFirstName(resultSet.getString(FIRST_NAME));
                intern.setLastName(resultSet.getString(LAST_NAME));
                intern.setEmail(resultSet.getString(EMAIL));
                intern.setGroupId(resultSet.getInt(GROUP_ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return intern;
    }

    @Override
    public boolean postIntern(Intern intern) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTERN)) {
            preparedStatement.setInt(1, intern.getId());
            preparedStatement.setString(2, intern.getFirstName());
            preparedStatement.setString(3, intern.getLastName());
            preparedStatement.setString(4, intern.getEmail());
            preparedStatement.setInt(5, intern.getGroupId());

            if(preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteIntern(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INTERN)) {
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean patchIntern(Intern intern) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INTERN)) {
            preparedStatement.setString(1, intern.getFirstName());
            preparedStatement.setString(2, intern.getLastName());
            preparedStatement.setString(3, intern.getEmail());
            preparedStatement.setInt(4, intern.getGroupId());
            preparedStatement.setInt(5, intern.getId());

            if(preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Group> getGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        Group group;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GROUPS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt(ID));
                group.setName(resultSet.getString(NAME));
                group.setPeriodStart(resultSet.getString(PERIOD_START));
                group.setPeriodFinish(resultSet.getString(PERIOD_FINISH));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }
}
