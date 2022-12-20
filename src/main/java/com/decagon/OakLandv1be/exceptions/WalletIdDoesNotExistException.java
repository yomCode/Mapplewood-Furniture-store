package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletIdDoesNotExistException extends RuntimeException{
    public WalletIdDoesNotExistException(Long walletId) {
        super("Wallet with walletId : "+walletId+" does not exist");
    }
}
