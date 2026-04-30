package com.example.MiniTradingApp.rest;

import com.example.MiniTradingApp.user.UserAsset;
import com.example.MiniTradingApp.user.UserDetails;

import java.util.List;

public class UserSummary {
    private UserDetails userDetails;
    private List<UserAsset> assets;
    private double totalAssetValue;

    public UserSummary(UserDetails userDetails, List<UserAsset> assets) {
        this.userDetails = userDetails;
        this.assets = assets;
        this.totalAssetValue = 0;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<UserAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<UserAsset> assets) {
        this.assets = assets;
    }

    public double getTotalAssetValue() {
        return totalAssetValue;
    }

    public void setTotalAssetValue(double totalAssetValue) {
        this.totalAssetValue = totalAssetValue;
    }
}
