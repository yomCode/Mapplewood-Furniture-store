package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.utils.ApiResponse;

public interface WalletService {

    //fund-wallet
    FundWalletResponseDto fundWallet(FundWalletRequest request);

    Double getWalletBalance();

}
