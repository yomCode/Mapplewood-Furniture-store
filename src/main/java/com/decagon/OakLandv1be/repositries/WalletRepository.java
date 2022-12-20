package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    //Wallet findWalletById( Integer walletId);


    //Optional<Wallet> findWalletById(@Param("walletId") Integer walletId);

//    Wallet findWalletByEmail(@Param("id"))Integer;
}