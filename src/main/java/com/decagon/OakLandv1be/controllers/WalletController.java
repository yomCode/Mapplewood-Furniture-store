package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/fund")
    public ApiResponse<Object> fundWallet(FundWalletRequest request){
        FundWalletResponseDto response = walletService.fundWallet(request);
        return new ApiResponse("Request successful", response, HttpStatus.OK);
    }

}
