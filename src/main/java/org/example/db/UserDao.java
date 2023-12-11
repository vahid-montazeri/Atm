package org.example.db;

import org.example.enums.Gender;
import org.example.model.User;

import java.sql.*;

public class UserDao implements Dao<User> {

    @Override
    public Long save(User user) {
        long id = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "INSERT INTO user (`name`, `family`, `national_code`, `address`, `phone_number`, `gender`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getName());
            statement.setString(2, user.getFamily());
            statement.setString(3, user.getNationalCode());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getGender().name());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }

            statement.close();
            connection.close();

            return id;

        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println(exception.getMessage());
        }

        return null;
    }

    @Override
    public User getById(Long id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "SELECT * from user where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long userId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String family = resultSet.getString("family");
                String nationalCode = resultSet.getString("national_code");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phone_number");
                String gender = resultSet.getString("gender");

                User user = new User();

                user.setId(userId);
                user.setName(name);
                user.setFamily(family);
                user.setNationalCode(nationalCode);
                user.setAddress(address);
                user.setPhoneNumber(phoneNumber);

                user.setGender(gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE);

                statement.close();
                connection.close();

                return user;
            }

        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void update(User user, Long id) {

        User existingUser = getById(id);

        String newName = user.getName();
        String newFamily = user.getFamily();
        String newNationalCode = user.getNationalCode();
        String newNumberPhone = user.getPhoneNumber();

        existingUser.setName(newName);
        existingUser.setFamily(newFamily);
        existingUser.setNationalCode(newNationalCode);
        existingUser.setPhoneNumber(newNumberPhone);

        save(existingUser);

    }

    @Override
    public void deleteById(Long id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "DELETE FROM user WHERE id = ?";
            PreparedStatement statment = connection.prepareStatement(query);
            statment.setLong(1, id);
            statment.executeQuery();
            statment.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println();
        }

    }


}