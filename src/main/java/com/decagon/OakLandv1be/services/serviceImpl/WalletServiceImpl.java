package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.InsufficientBalanceInWalletException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

import static com.decagon.OakLandv1be.enums.TransactionStatus.SUCCESSFUL;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    private final JavaMailService mailService;

    @Value("${admin.super.email}")
    private String superAdminEmail;





    @Override
    public FundWalletResponseDto fundWallet(FundWalletRequest request) {
            Person person = personRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Wallet wallet = person.getCustomer().getWallet();
            wallet.setAccountBalance(wallet.getAccountBalance().add(request.getAmount()));
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

       // throw new UnauthorizedUserException("Login to carry out this operation");
    }


    @Override
    public BigDecimal getWalletBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Wallet wallet = person.getCustomer().getWallet();
            BigDecimal walletBalance = wallet.getAccountBalance();

            try {
                mailService.sendMail(person.getEmail(), "Wallet Balance", "Your wallet is " + walletBalance + wallet.getBaseCurrency());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return walletBalance;
        }
        throw new UnauthorizedUserException("User does not have access");
    }
    @Override
    public Boolean processPayment(BigDecimal grandTotal){

        //get the logged in customer wallet
        Wallet customerWallet=getCurrentlyLoggedInCustomerWallet();

        //confirm if the current balance is enough
        if(customerWallet.getAccountBalance().compareTo(grandTotal)>=0){
            //deduct the ground total from the wallet balance
            customerWallet.setAccountBalance(customerWallet.getAccountBalance().subtract(grandTotal));

            //get the admin wallet
            Wallet adminWallet=personRepository.findByEmail(superAdminEmail).orElseThrow(
                    ()-> new UserNotFoundException("System Error: Please contact the admin")
            ).getSuperAdmin().getWallet();
            // add the ground total to super admin wallet
            adminWallet.setAccountBalance(adminWallet.getAccountBalance().add(grandTotal));

            // sent an email to the customer
            //TODO         <=================================
            try {

                String customerEmail= UserUtil.extractEmailFromPrincipal().orElseThrow(
                        ()->new UsernameNotFoundException("Please login to continue")
                );

                mailService.sendMail(customerEmail, "Transaction Alert", "Transaction Amount is "  + grandTotal);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return true;

        }

        throw new InsufficientBalanceInWalletException("Insufficient balance, Please fund your wallet.");


    }



    Wallet getCurrentlyLoggedInCustomerWallet(){
        return personRepository.findByEmail(UserUtil.extractEmailFromPrincipal().orElseThrow(
                ()->new UsernameNotFoundException("Please login to continue")
        )).orElseThrow(
                ()-> new UserNotFoundException("Please login to continue")
        ).getCustomer().getWallet();
    }

    //get the currently logged in customer

//        String customerEmail= UserUtil.extractEmailFromPrincipal().orElseThrow(
//                ()->new UsernameNotFoundException("Please login to continue")
//        );
//
//        Person loggedInPerson=personRepository.findByEmail(customerEmail).orElseThrow(
//                ()-> new UserNotFoundException("Please login to continue")
//        );
//
//        Customer customer=loggedInPerson.getCustomer();


}
