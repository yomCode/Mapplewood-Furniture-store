package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.http.ResponseEntity;


public interface WalletService {

    FundWalletResponseDto fundWallet(FundWalletRequest request);

    Double getWalletBalance();

}
