package com.example.MiniTradingApp.rest;

import com.example.MiniTradingApp.user.UserAsset;
import com.example.MiniTradingApp.user.UserAssetRepository;
import com.example.MiniTradingApp.user.UserDetails;
import com.example.MiniTradingApp.user.UserDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSummaryService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserAssetRepository userAssetRepository;

    public UserSummaryService(
            UserDetailsRepository userDetailsRepository,
            UserAssetRepository userAssetRepository
    ) {
        this.userDetailsRepository = userDetailsRepository;
        this.userAssetRepository = userAssetRepository;
    }

    public UserSummary getSummary(String username) {
        Optional<UserDetails> userDetails = userDetailsRepository.findByUsername(username);
        if (userDetails.isEmpty()) {
            throw new RuntimeException("No details found for " + username);
        }

        List<UserAsset> assets = userAssetRepository.findByUserDetails(userDetails.get());

        UserSummary userSummary = new UserSummary(userDetails.get(), assets);
        userSummary.setTotalAssetValue(getTotalAssetValue(assets));
        return userSummary;
    }

    private double getTotalAssetValue(List<UserAsset> assets){
        return assets.stream().mapToDouble(UserAsset::getStockValue).sum();
    }

}
