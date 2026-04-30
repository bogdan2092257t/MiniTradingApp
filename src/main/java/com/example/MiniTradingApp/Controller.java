package com.example.MiniTradingApp;

import com.example.MiniTradingApp.order.UserOrder;
import com.example.MiniTradingApp.order.UserOrderRepository;
import com.example.MiniTradingApp.rest.UserSummary;
import com.example.MiniTradingApp.rest.UserSummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class Controller {

    private final UserSummaryService userSummaryService;
    private final UserOrderRepository userOrderRepository;

    public Controller(UserSummaryService userSummaryService,
                      UserOrderRepository userOrderRepository) {
        this.userSummaryService = userSummaryService;
        this.userOrderRepository = userOrderRepository;
    }

    @GetMapping("/")
    public UserSummary summary(Principal principal) {
        String username = principal.getName();

        return userSummaryService.getSummary(username);
    }

    @GetMapping("/orders")
    public List<UserOrder> orders(Principal principal) {
        String username = principal.getName();

        return userOrderRepository.findAllByUsername(username);
    }
}

