package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface WalletService {
    ResponseEntity<ApiResponse<Object>> fundWallet(FundWalletRequest request) throws IOException;
}
