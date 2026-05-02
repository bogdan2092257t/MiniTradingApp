package com.example.MiniTradingApp.order.kafka;

import com.example.MiniTradingApp.order.OrderSettlementService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PendingTradeConsumer {

    private final OrderSettlementService orderSettlementService;

    public PendingTradeConsumer(OrderSettlementService orderSettlementService) {
        this.orderSettlementService = orderSettlementService;
    }

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(
            topics = "${app.kafka.pending-trades-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumePendingTrade(PendingTradeMessage order) {
        System.out.printf(
                """
                        %s Pending trade:
                          id: %d
                          ticker: %s
                          direction: %s
                          quantity: %d
                          pricePerShare: %.2f
                          status: %s
                        %n""",
                LocalDateTime.now().format(TIMESTAMP_FORMATTER),
                order.id(),
                order.ticker(),
                order.direction(),
                order.quantity(),
                order.pricePerShare(),
                order.status()
        );

        this.orderSettlementService.settleOrder(order.id(), order.direction(), order.quantity(), order.pricePerShare());
    }
}
