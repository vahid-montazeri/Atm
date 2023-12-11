package org.example.model;

import java.sql.Date;

public class Card {
    private String cardNumber;
    private String irNumber;
    private Integer cvv2;
    private Date expireDate;
    private User user;

    public Card() {

    }

    public Card(String cardNumber, String irNumber, Integer cvv2, Date expireDate, User user) {
        this.cardNumber = cardNumber;
        this.irNumber = irNumber;
        this.cvv2 = cvv2;
        this.expireDate = expireDate;
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIrNumber() {
        return irNumber;
    }

    public void setIrNumber(String irNumber) {
        this.irNumber = irNumber;
    }

    public Integer getCvv2() {
        return cvv2;
    }

    public void setCvv2(Integer cvv2) {
        this.cvv2 = cvv2;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

