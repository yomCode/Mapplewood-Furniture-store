package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/fund-wallet")
    public ResponseEntity<ApiResponse<Object>> fundWallet(@RequestBody FundWalletRequest request){
        return walletService.fundWallet(request);
    }
}
