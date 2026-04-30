package com.example.MiniTradingApp.user;

public class UserAsset {
    private Stock stock;
    private int quantity;
    private double pricePerShare;
    private double stockValue;

    public UserAsset( Stock stock, int quantity, double pricePerShare, double stockValue) {
        this.stock = stock;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.stockValue = stockValue;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }
}
