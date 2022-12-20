package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class WalletController {


    private final WalletService walletService;

    @PostMapping("/wallet-withdraw")
    ResponseEntity<String> withdrwalFromWallet(Double amount) {
        return walletService.withdrwalFromWallet(amount);
    }
}
