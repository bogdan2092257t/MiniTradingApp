package com.example.MiniTradingApp.order;

public class UserOrder {
    private long id;
    private long userId;
    private String ticker;
    private OrderDirection direction;
    private int quantity;
    private double pricePerShare;
    private OrderStatus status;
    private String rejectionReason;

    public UserOrder(String ticker, OrderDirection direction, int quantity, double pricePerShare) {
        this.ticker = ticker;
        this.direction = direction;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.status = OrderStatus.PENDING;
    }

    public UserOrder(long id,
                     long userId,
                     String ticker,
                     OrderDirection direction,
                     int quantity,
                     double pricePerShare,
                     OrderStatus status,
                     String rejectionReason) {
        this.id = id;
        this.userId = userId;
        this.ticker = ticker;
        this.direction = direction;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.status = status;
        this.rejectionReason = rejectionReason;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OrderDirection getDirection() {
        return direction;
    }

    public void setDirection(OrderDirection direction) {
        this.direction = direction;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
