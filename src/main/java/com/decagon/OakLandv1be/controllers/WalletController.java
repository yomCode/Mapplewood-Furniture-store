package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.cartDtos.ProcessPaymentRequest;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/fund")
    public ApiResponse<Object> fundWallet(@RequestBody FundWalletRequest request){
        return new ApiResponse("Request successful", walletService.fundWallet(request), HttpStatus.OK);
    }


    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<Object>> getBalance(){
        BigDecimal response = walletService.getWalletBalance();
        return new ResponseEntity<>(new ResponseManager().success(response), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ApiResponse<Object> walletInfo(){

        return new ApiResponse<>("Request successful", walletService.viewWalletInfo(), HttpStatus.OK);
    }

    @PutMapping("/process-payment")
    public  ResponseEntity<Boolean> processPayment(@RequestBody ProcessPaymentRequest processPaymentyRequest){
        return ResponseEntity.ok(walletService.processPayment(processPaymentyRequest.getGrandTotal()));
    }

}
