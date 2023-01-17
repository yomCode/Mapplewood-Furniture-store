package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.decagon.OakLandv1be.enums.TransactionStatus.SUCCESSFUL;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final JavaMailService mailService;

    @Override
    public FundWalletResponseDto fundWallet(FundWalletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String email = authentication.getName();
            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Wallet wallet = person.getCustomer().getWallet();
            wallet.setAccountBalance(wallet.getAccountBalance() + request.getAmount());
            walletRepository.save(wallet);

            Transaction transaction = Transaction.builder()
                            .wallet(wallet)
                                    .status(SUCCESSFUL).build();
            transactionRepository.save(transaction);

            try {
                mailService.sendMail(person.getEmail(), "Wallet deposit", "Your wallet has been credited with "
                        + request.getAmount() + ". Your new balance is now " + wallet.getAccountBalance());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return FundWalletResponseDto.builder()
                    .fullName(person.getFirstName() + " " + person.getLastName())
                    .depositAmount(request.getAmount())
                    .newBalance(wallet.getAccountBalance()).build();
        }
        throw new UnauthorizedUserException("Login to carry out this operation");
    }

    @Override
    public Double getWalletBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Wallet wallet = person.getCustomer().getWallet();
            Double walletBalance = wallet.getAccountBalance();

            try {
                mailService.sendMail(person.getEmail(), "Wallet Balance", "Your wallet is " + walletBalance + wallet.getBaseCurrency());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return walletBalance;
        }
        throw new UnauthorizedUserException("User does not have access");
    }

}
