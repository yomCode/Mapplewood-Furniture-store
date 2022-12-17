package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;

public interface WalletService {
    String fundWallet(FundWalletRequest request);
}
