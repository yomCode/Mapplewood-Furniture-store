package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}