package com.server.controller.db;

import com.entity.Entity;
import com.entity.Group;
import com.entity.Intern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public ArrayList<Intern> selectAllInterns() {
        ArrayList<Intern> interns = new ArrayList<>();
        Intern intern;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INTERNS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                intern = createIntern(resultSet);
                interns.add(intern);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return selectItems(SELECT_ALL_INTERNS, interns, Intern.class);
        return interns;
    }

//    private <T extends Entity> ArrayList<T> selectItems(String itemQuery, ArrayList<T> list, Class<T> className) {
//        try(PreparedStatement preparedStatement = connection.prepareStatement(itemQuery)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            Constructor<T> con = className.getDeclaredConstructor(className);
//            T item = (T) con.newInstance();
//            while(resultSet.next()) {
//                item = (T) createIntern(resultSet);
//                list.add(item);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    private Intern createIntern(ResultSet resultSet) throws SQLException {
        Intern intern = new Intern();
        intern.setId(resultSet.getInt(ID));
        intern.setFirstName(resultSet.getString(FIRST_NAME));
        intern.setLastName(resultSet.getString(LAST_NAME));
        intern.setEmail(resultSet.getString(EMAIL));
        intern.setGroupId(resultSet.getInt(GROUP_ID));
        return intern;
    }

    @Override
    public Intern selectInternsById(int id) {
        Intern intern = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INTERN_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                intern = createIntern(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return intern;
    }

    @Override
    public boolean insertIntern(Intern intern) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTERN)) {
            if(prepareInsert(preparedStatement, intern).executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PreparedStatement prepareInsert(PreparedStatement preparedStatement, Intern intern) throws SQLException {
        preparedStatement.setInt(1, intern.getId());
        preparedStatement.setString(2, intern.getFirstName());
        preparedStatement.setString(3, intern.getLastName());
        preparedStatement.setString(4, intern.getEmail());
        preparedStatement.setInt(5, intern.getGroupId());
        return preparedStatement;
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
    public boolean updateIntern(Intern intern) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INTERN)) {
            if(prepareUpdate(preparedStatement, intern).executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PreparedStatement prepareUpdate(PreparedStatement preparedStatement, Intern intern) throws SQLException {
        preparedStatement.setString(1, intern.getFirstName());
        preparedStatement.setString(2, intern.getLastName());
        preparedStatement.setString(3, intern.getEmail());
        preparedStatement.setInt(4, intern.getGroupId());
        preparedStatement.setInt(5, intern.getId());
        return preparedStatement;
    }

    @Override
    public ArrayList<Group> selectGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        Group group;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GROUPS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                group = createGroup(resultSet);
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    private Group createGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt(ID));
        group.setName(resultSet.getString(NAME));
        group.setPeriodStart(resultSet.getString(PERIOD_START));
        group.setPeriodFinish(resultSet.getString(PERIOD_FINISH));
        return group;
    }
}
