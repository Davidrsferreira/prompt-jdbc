package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.utils.Security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class JdbcUserService implements UserService {

    @Override
    public boolean authenticate(String username, String password) {

        String query = "SELECT * FROM users WHERE user_name=?";

        PreparedStatement statement = null;

        ResultSet resultSet;

        User user = new User();

        try {

            statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, username);

            resultSet = statement.executeQuery();

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

        String query = "INSERT INTO users VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = null;

        try {

            statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getPhone());

            statement.executeUpdate();

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
    public User findByName(String userName) {

        String query = "SELECT * FROM users WHERE user_name = ?";

        ResultSet resultSet;

        User user = new User();

        PreparedStatement statement = null;

        try {

            statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, userName);

            resultSet = statement.executeQuery();

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

        PreparedStatement statement = null;

        try {

            statement = ConnectionManager.getConnection().prepareStatement(query);
            resultSet = statement.executeQuery();

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

        PreparedStatement statement = null;

        try {

            statement = ConnectionManager.getConnection().prepareStatement(query);
            resultSet = statement.executeQuery();

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
