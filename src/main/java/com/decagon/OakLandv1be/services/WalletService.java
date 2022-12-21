package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.WithdrawDto;
import org.springframework.http.ResponseEntity;

public interface WalletService {
  ResponseEntity<String> withdrawalFromWallet(WithdrawDto withdrawDto);
}
