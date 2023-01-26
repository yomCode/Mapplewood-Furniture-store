package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.dto.WalletInfoResponseDto;
import com.decagon.OakLandv1be.utils.ApiResponse;

import java.math.BigDecimal;

public interface WalletService {

    FundWalletResponseDto fundWallet(FundWalletRequest request);

    BigDecimal getWalletBalance();

    WalletInfoResponseDto viewWalletInfo();
}
