package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Wallet;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ResponseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {



    @Mock
    private PersonRepository personRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ResponseManager responseManager;

    @Mock
    private JavaMailService mailService;

//    @Mock
    Person person;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Wallet wallet = new Wallet();
        Customer customer = new Customer();
        wallet.setAccountBalance(0.00);
        wallet.setCustomer(customer);
        wallet.setBaseCurrency(BaseCurrency.DOLLAR);

        person = Person.builder()
                .customer(customer)
                .email("abc@gmail.com")
                .address("lagos")
                .gender(Gender.MALE)
                .phone("1234567890")
                .date_of_birth("10/10/1990")
                .firstName("Yomi")
                .lastName("Musty")
                .password("123456789")
                .verificationStatus(true)
                .role(Role.CUSTOMER)
                .build();

        Mockito.when(personRepository.findByEmail(any())).thenReturn(Optional.of(person));


    }

    @Test
    void fundWallet() {
        FundWalletRequest request = new FundWalletRequest(10000.00);
        FundWalletResponseDto response = FundWalletResponseDto.builder().fullName(person.getFirstName() + " " + person.getLastName())
                .depositAmount(request.getAmount())
                        .newBalance(person.getCustomer().getWallet().getAccountBalance()).build();
//        walletService.fundWallet(request);
        Mockito.when(walletService.fundWallet(request)).thenReturn(response);

        assertEquals(response, walletService.fundWallet(request));

    }
}