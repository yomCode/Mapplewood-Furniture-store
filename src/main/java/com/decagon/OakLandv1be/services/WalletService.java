package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;

import java.math.BigDecimal;

public interface WalletService {

    FundWalletResponseDto fundWallet(FundWalletRequest request);
    //ResponseEntity<ApiResponse<Object>> fundWallet(String email, BigDecimal amount);

    BigDecimal getWalletBalance();

}
