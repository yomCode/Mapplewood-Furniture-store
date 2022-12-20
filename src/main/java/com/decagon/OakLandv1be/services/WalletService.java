package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.entities.Wallet;
import org.springframework.http.ResponseEntity;

public interface WalletService {
  ResponseEntity<String> withdrwalFromWallet(Double amount);
}
