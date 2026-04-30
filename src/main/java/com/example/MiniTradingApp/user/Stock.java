package com.example.MiniTradingApp.user;

public class Stock {
    private Long id;
    private String ticker;

    public Stock(Long id, String ticker) {
        this.id = id;
        this.ticker = ticker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
