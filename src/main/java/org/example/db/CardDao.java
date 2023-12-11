package org.example.db;

import org.example.model.Card;

import java.sql.*;

public class CardDao implements Dao<Card> {


    @Override
    public Long save(Card card) {
        long id = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "INSERT INTO card (`card_number`,`ir_number`,ccv2,expire_date) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getIrNumber());
            statement.setLong(3, card.getCvv2());
            statement.setDate(4, card.getExpireDate());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating card failed, no rows affected.");
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
    public Card getById(Long id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "SELECT * from card where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                String card_number = resultSet.getString("card_number");
                String ir_number = resultSet.getString("ir_number");
                String ccv2 = resultSet.getString("ccv2");
                Date expire_date = resultSet.getDate("expire_number");

                Card card = new Card();

                card.setCardNumber(card_number);
                card.setIrNumber(ir_number);
                card.setCvv2(Integer.valueOf(ccv2));
                card.setExpireDate(expire_date);


//                if (gender.equalsIgnoreCase("male")) {
//                    account.setGender(Gender.MALE);
//                } else if (gender.equalsIgnoreCase("female")) {
//                    account.setGender(Gender.FEMALE);
//                }

                statement.close();
                connection.close();

                return card;
            }

        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void update(Card card, Long id) {
        Card existingCard = getById(id);
        String newCardNumber = card.getCardNumber();
        String newIrNumber = card.getIrNumber();
        Integer newCvv2 = card.getCvv2();
        Date newExpireDate = card.getExpireDate();

        existingCard.setCardNumber(newCardNumber);
        existingCard.setIrNumber(newIrNumber);
        existingCard.setCvv2(newCvv2);
        existingCard.setExpireDate(newExpireDate);
        save(existingCard);


    }

    @Override
    public void deleteById(Long id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
            String query = "DELETE FROM user WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeQuery();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException exception) {
            System.err.println();
        }

    }
}