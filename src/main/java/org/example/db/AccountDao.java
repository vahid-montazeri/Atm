package org.example.db;

import org.example.model.Account;

import java.sql.*;

public class AccountDao implements Dao<Account> {

    @Override
    public Long save(Account account) {
        long id = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "INSERT INTO account (`account_number`,`balance`, `user_id`) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, account.getAccountNumber());
            statement.setLong(2, account.getBalance());
            statement.setLong(3, account.getUser().getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
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
    public Account getById(Long id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "SELECT * from account where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                String accountNumber = resultSet.getString("account_number");
                String balance = resultSet.getString("balance");


                Account account = new Account();

                account.setAccountNumber(accountNumber);
                account.setAccountNumber(balance);


//                if (gender.equalsIgnoreCase("male")) {
//                    account.setGender(Gender.MALE);
//                } else if (gender.equalsIgnoreCase("female")) {
//                    account.setGender(Gender.FEMALE);
//                }

                statement.close();
                connection.close();

                return account;
            }

        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void update(Account account, Long id) {
        Account existingAccount = getById(id);

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.map(account, existingAccount);

        Long newBalance = account.getBalance();
        String newAccountNumber = account.getAccountNumber();

        existingAccount.setBalance(newBalance);
        existingAccount.setAccountNumber(newAccountNumber);

        save(existingAccount);
    }

    @Override
    public void deleteById(Long id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "DELETE FROM account WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println();
        }

    }

    public Double getBalanceByAccountNumber(int accountNumber) {
        double balance = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "SELECT balance from account where account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getLong("balance");
            }
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return balance;
    }

    public void updateBalance(int accountNumber, Double newBalance) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "UPDATE account SET balance = ? WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDouble(1, newBalance);
            statement.setInt(2, accountNumber);
            statement.executeUpdate();
            statement.close();
            connection.close();

            System.out.println("Updated successfully");

        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println(exception.getMessage());
        }


    }

}