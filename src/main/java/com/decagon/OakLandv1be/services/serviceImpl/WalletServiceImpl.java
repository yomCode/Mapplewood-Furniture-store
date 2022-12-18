package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Transaction;
import com.decagon.OakLandv1be.entities.Wallet;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.decagon.OakLandv1be.enums.TransactionStatus.SUCCESSFUL;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final PersonRepository personRepository;

    private final WalletRepository walletRepository;

    private final TransactionRepository transactionRepository;


    @Override
    public String fundWallet(FundWalletRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Person not found"));

            Wallet wallet = person.getCustomer().getWallet();
            wallet.setAccountBalance(wallet.getAccountBalance() + request.getAmount());

            Transaction transaction = Transaction.builder()
                            .wallet(wallet)
                                    .status(SUCCESSFUL).build();
            transactionRepository.save(transaction);

            wallet.getTransactions().add(transaction);
            walletRepository.save(wallet);

            return "Wallet funded successfully";
        }
        return "Invalid user";
    }


}
