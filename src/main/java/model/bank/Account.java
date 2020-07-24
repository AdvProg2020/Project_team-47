package model.bank;

import java.util.ArrayList;

public class Account {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private double money;
    private final int id;
    private final ArrayList<Transaction> sourceTransactions;
    private final ArrayList<Transaction> destTransactions;

    public Account(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        money = 0;
        id = Bank.getInstance().getAccounts().size();
        sourceTransactions = new ArrayList<>();
        destTransactions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Transaction> getSourceTransactions() {
        return sourceTransactions;
    }

    public ArrayList<Transaction> getDestTransactions() {
        return destTransactions;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getMoney() {
        return money;
    }

    public void addMoney(double money) {
        this.money = this.money + money;
    }
}
