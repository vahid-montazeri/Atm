package org.example.model;

public class Account {
    private String accountNumber;
    private Long balance;
    public User user;

    public Account() {

    }

    public Account(String accountNumber, Long balance, User user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
