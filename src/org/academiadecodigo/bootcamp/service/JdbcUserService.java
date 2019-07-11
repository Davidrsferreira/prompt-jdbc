package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.utils.Security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class JdbcUserService implements UserService {

    @Override
    public boolean authenticate(String username, String password) {

        String query = "SELECT * FROM users WHERE user_name = '" + username + "'";

        ResultSet resultSet;

        User user = new User();

        Statement statement = null;

        try {

            statement = ConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                user.setPassword(resultSet.getString("password"));

                if (user.getPassword().equals(Security.getHash(password))){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

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

        Statement statement = null;

        try {

            statement = ConnectionManager.getConnection().createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public User findByName(String username) {

        String query = "SELECT * FROM users WHERE user_name = '" + username + "'";

        ResultSet resultSet;

        User user = new User();

        Statement statement = null;

        try {

            statement = ConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                user.setUsername(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPhone(resultSet.getString("phone"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    @Override
    public List<User> findAll() {

        String query = "SELECT * FROM users";

        ResultSet resultSet;

        LinkedList<User> users = new LinkedList<>();

        Statement statement = null;

        try {

            statement = ConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                User user = new User();

                user.setUsername(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPhone(resultSet.getString("phone"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    @Override
    public int count() {

        int result = 0;

        String query = "SELECT COUNT(*) FROM users";

        ResultSet resultSet;

        Statement statement = null;

        try {

            statement = ConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            // get the results
            if (resultSet.next()) {
                result = resultSet.getInt("user_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
