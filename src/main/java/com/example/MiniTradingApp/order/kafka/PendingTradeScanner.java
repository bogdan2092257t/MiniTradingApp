package com.example.MiniTradingApp.order.kafka;

import com.example.MiniTradingApp.order.UserOrder;
import com.example.MiniTradingApp.order.UserOrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PendingTradeScanner {

    private final UserOrderRepository userOrderRepository;
    private final KafkaTemplate<String, PendingTradeMessage> kafkaTemplate;
    private final String pendingTradesTopic;

    public PendingTradeScanner(
            UserOrderRepository userOrderRepository,
            KafkaTemplate<String, PendingTradeMessage> kafkaTemplate,
            @Value("${app.kafka.pending-trades-topic}") String pendingTradesTopic
    ) {
        this.userOrderRepository = userOrderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.pendingTradesTopic = pendingTradesTopic;
    }

    @Scheduled(fixedRate = 60_000, initialDelay = 0)
    public void scanPendingTrades() {
        List<UserOrder> pendingOrders = userOrderRepository.findPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println("No pending trades found.");
            return;
        }

        System.out.println("Pending trades found: " + pendingOrders.size());

        for (UserOrder order : pendingOrders) {
            PendingTradeMessage message = PendingTradeMessage.from(order);

            kafkaTemplate.send(
                    pendingTradesTopic,
                    String.valueOf(order.getId()),
                    message
            );
        }
    }
}
