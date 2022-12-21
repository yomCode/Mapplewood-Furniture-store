package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.WithdrawDto;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Transaction;
import com.decagon.OakLandv1be.entities.Wallet;
import com.decagon.OakLandv1be.exceptions.InsufficientBalanceInWalletException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.exceptions.WalletIdDoesNotExistException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final JavaMailService mailService;

    private final TransactionRepository transactionRepository;


    @Override
    public ResponseEntity<String> withdrawalFromWallet(WithdrawDto withdrawDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            Long walletId = person.getCustomer().getWallet().getId();

            Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletIdDoesNotExistException(walletId));

            if (wallet.getAccountBalance() < withdrawDto.getAmount())
                throw new InsufficientBalanceInWalletException();

                wallet.setAccountBalance(wallet.getAccountBalance() - withdrawDto.getAmount());

            Transaction transaction = Transaction.builder()
                        .wallet(wallet)
                        .status(SUCCESSFUL)
                        .build();

                transactionRepository.save(transaction);
                wallet.getTransactions().add(transaction);
                walletRepository.save(wallet);

                try {
                    mailService.sendMail(person.getEmail(), "Wallet deposit", "Your wallet has been debited with "
                            + withdrawDto + ". Your new balance is now " + wallet.getAccountBalance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return new ResponseEntity<>("Wallet funded successfully", HttpStatus.OK);
            }

        throw new UnauthorizedUserException("Login to carry out this operation");
    }
}

























//
//    @Override
//    public Wallet withdrwalFromWallet(Integer accountId, Double amount, String type) {
//        return null;



        //   @Override
//    public Wallet withdrwalFromWallet(Integer accountId, Double amount) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String email = authentication.getName();
//
////            Person person = personRepository.findByEmail(email)
////                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
//
//
//            Wallet wallet = walletRepository.findWalletById(walletId).getCustomer().getWallet();
//            if (wallet == null) {
//                throw new WalletIdDoesNotExistException(walletId);
//            }
//            if (wallet.getAccountBalance() < amount) {
//                throw new InsufficientBalanceInWalletException();
//            }
//            Double currentBalance = wallet.getAccountBalance();
//            wallet.setAccountBalance(currentBalance - amount);
//
//             walletRepository.save(wallet);
//            Transaction transaction =


        // Wallet wallet1 = walletRepository.findWalletById(walletId);
//        Wallet wallet = walletRepository.findWalletById(walletId);
//        if(wallet==null){
//            throw new WalletIdDoesNotExistException(walletId);
//        }
//        if(wallet.get(0))
//       return null;
//    }
//}
//
