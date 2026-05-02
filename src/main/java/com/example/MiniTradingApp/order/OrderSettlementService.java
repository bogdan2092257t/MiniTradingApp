package com.example.MiniTradingApp.order;

import com.example.MiniTradingApp.user.UserAsset;
import com.example.MiniTradingApp.user.UserAssetRepository;
import com.example.MiniTradingApp.user.UserDetails;
import com.example.MiniTradingApp.user.UserDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderSettlementService {

    private final UserOrderRepository userOrderRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserAssetRepository userAssetRepository;

    public OrderSettlementService(UserOrderRepository userOrderRepository, UserDetailsRepository userDetailsRepository, UserAssetRepository userAssetRepository) {
        this.userOrderRepository = userOrderRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userAssetRepository = userAssetRepository;
    }


    @Transactional
    public void settleOrder(long orderId, OrderDirection direction, int quantity, double pricePerShare){
        Optional<UserOrder> orderOptional = userOrderRepository.findByOrderId(orderId);

        if(orderOptional.isEmpty()){
            System.out.println("Could not find order in the database: " + orderId);
        }
        UserOrder order = orderOptional.get();
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.findByUserId(order.getUserId());

        if(userDetailsOptional.isEmpty()){
            System.out.println("Could not find user detail in the database: " + order.getUserId());
        }
        UserDetails userDetails = userDetailsOptional.get();
        if(direction==OrderDirection.BUY)
            settleBuyOrder(orderId, userDetails,quantity,pricePerShare,order.getTicker());
        else
            settleSellOrder(orderId, userDetails,quantity,pricePerShare,order.getTicker());
    }

    private void settleSellOrder(long orderId, UserDetails userDetails, int quantity, double pricePerShare, String ticker) {
        Optional<UserAsset> asset = userAssetRepository.findByUserDetailsAndTicker(userDetails,ticker);
        if(asset.isEmpty() || asset.get().getQuantity()<quantity){
            userOrderRepository.rejectOrder(orderId, "Not enough assets to satisfy sell order");
            return;
        }
        int newQuantity = asset.get().getQuantity() - quantity;
        if(newQuantity==0)
            userAssetRepository.delete(userDetails,ticker);
        else
            userAssetRepository.updateQuantityAndPricePerShare(userDetails,ticker,quantity, pricePerShare);

        double newBalance = userDetails.getBalance()+quantity*pricePerShare;
        userDetailsRepository.updateBalance(userDetails.getId(), newBalance);
        userOrderRepository.approve(orderId);

    }

    private void settleBuyOrder(long orderId, UserDetails userDetails, int quantity, double pricePerShare, String ticker) {
        if(userDetails.getBalance() < quantity*pricePerShare){
            userOrderRepository.rejectOrder(orderId, "Balance too low for this purchase");
            return;
        }

        Optional<UserAsset> asset = userAssetRepository.findByUserDetailsAndTicker(userDetails,ticker);
        if(asset.isEmpty()){
            userAssetRepository.save(userDetails,ticker, quantity, pricePerShare);
        } else {
            int newQuantity = asset.get().getQuantity() + quantity;
            userAssetRepository.updateQuantityAndPricePerShare(userDetails,ticker,newQuantity,pricePerShare);
        }
        double newBalance = userDetails.getBalance()-quantity*pricePerShare;
        userDetailsRepository.updateBalance(userDetails.getId(), newBalance);
        userOrderRepository.approve(orderId);
    }
}
