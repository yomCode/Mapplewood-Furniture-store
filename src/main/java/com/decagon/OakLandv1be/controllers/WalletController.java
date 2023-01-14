package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Double response = walletService.getWalletBalance();

        return new ResponseEntity<>(new ResponseManager().success(response), HttpStatus.OK);

    }

}
