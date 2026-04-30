package com.example.MiniTradingApp.user;

public class UserDetails {
    private Long id;
    private String username;
    private String name;
    private double balance;

    public UserDetails(Long id, String username, String name, double balance) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
