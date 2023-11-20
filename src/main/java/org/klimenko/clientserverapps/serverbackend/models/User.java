package org.klimenko.clientserverapps.serverbackend.models;

import java.math.BigDecimal;

public class User {

    private int userID;
    private String name;
    private BigDecimal balance;
    private String hash;
    private String tray;

    public User(int userID, String name, String balance, String hash, String tray) {
        this.userID = userID;
        this.name = name;
        this.balance = new BigDecimal(balance);
        this.hash = hash;
        this.tray = tray;
    }

    public void setTray(String tray) {
        this.tray = tray;
    }

    public String getTray() {
        return tray;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(String balance) {
        this.balance = new BigDecimal(balance);
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getBalance() {
        return balance.toString();
    }

    public String getHash() {
        return hash;
    }
}
