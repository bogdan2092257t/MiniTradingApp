package com.example.MiniTradingApp.order.kafka;

import com.example.MiniTradingApp.order.OrderDirection;
import com.example.MiniTradingApp.order.OrderStatus;
import com.example.MiniTradingApp.order.UserOrder;

public record PendingTradeMessage(
        Long id,
        String ticker,
        OrderDirection direction,
        Integer quantity,
        double pricePerShare,
        OrderStatus status
) {
    public static PendingTradeMessage from(UserOrder order) {
        return new PendingTradeMessage(
                order.getId(),
                order.getTicker(),
                order.getDirection(),
                order.getQuantity(),
                order.getPricePerShare(),
                order.getStatus()
        );
    }
}
