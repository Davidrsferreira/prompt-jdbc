package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcUserService implements UserService {

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public void add(User user) {

        String query = "INSERT INTO users VALUES (\""
                + user.getUsername() + "\", \""
                + user.getEmail() + "\", \""
                + user.getPassword() + "\", \""
                + user.getFirstName() + "\", \""
                + user.getLastName() + "\", \""
                + user.getPhone()+ "\")";

        try {

            Statement statement = ConnectionManager.getConnection().createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public int count() {

        int result = 0;

        String query = "SELECT COUNT(*) FROM users";

        ResultSet resultSet = null;

        try {

            Statement statement = ConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            // get the results
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
