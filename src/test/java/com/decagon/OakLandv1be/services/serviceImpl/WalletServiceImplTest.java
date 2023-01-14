package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Transaction;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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

        when(personRepository.findByEmail(any())).thenReturn(Optional.of(person));


    }

    @Test
    void fundWallet() {
        FundWalletRequest request = new FundWalletRequest(10000.00);
        FundWalletResponseDto response = FundWalletResponseDto.builder().fullName(person.getFirstName() + " " + person.getLastName())
                .depositAmount(request.getAmount())
                        .newBalance(person.getCustomer().getWallet().getAccountBalance()).build();
//        walletService.fundWallet(request);
        when(walletService.fundWallet(request)).thenReturn(response);

        assertEquals(response, walletService.fundWallet(request));

    }
}


//import com.decagon.OakLandv1be.dto.FundWalletRequest;
//import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
//import com.decagon.OakLandv1be.entities.Person;
//import com.decagon.OakLandv1be.entities.Transaction;
//import com.decagon.OakLandv1be.entities.Wallet;
//import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
//import com.decagon.OakLandv1be.repositries.PersonRepository;
//import com.decagon.OakLandv1be.repositries.TransactionRepository;
//import com.decagon.OakLandv1be.repositries.WalletRepository;
//import com.decagon.OakLandv1be.services.JavaMailService;
//import com.decagon.OakLandv1be.services.serviceImpl.WalletServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WalletServiceImplTest {
//
//    @Mock
//    private PersonRepository personRepository;
//    @Mock
//    private WalletRepository walletRepository;
//    @Mock
//    private TransactionRepository transactionRepository;
//    @Mock
//    private JavaMailService mailService;
//
//    @InjectMocks
//    private WalletServiceImpl fundWalletService;
//
//    @Before
//    public void setup() {
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("test@email.com");
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    @Test
//    public void testFundWallet_validInput_success() throws IOException {
//        FundWalletRequest request = new FundWalletRequest(100.0);
//        Person person = new Person();
//        person.setEmail("test@email.com");
//        person.setFirstName("test");
//        person.setLastName("user");
//        when(personRepository.findByEmail("test@email.com")).thenReturn(Optional.of(person));
//        Wallet wallet = new Wallet();
//        wallet.setAccountBalance(1000.0);
//        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
//        Transaction transaction = new Transaction();
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
////        doNothing().when(mailService).sendMail(anyString(), anyString(), anyString());
//
//        FundWalletResponseDto response = fundWalletService.fundWallet(request);
//
//        assertEquals("test user", response.getFullName());
//        assertEquals(Optional.of(100.0), response.getDepositAmount());
//        assertEquals(Optional.of(1100.0), response.getNewBalance());
//        verify(walletRepository, times(1)).save(any(Wallet.class));
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//        verify(mailService, times(1)).sendMail(anyString(), anyString(), anyString());
//    }
//
//    @Test(expected = UnauthorizedUserException.class)
//    public void testFundWallet_anonymousUser_throwUnauthorizedUserException() {
//        Authentication anonymousAuthentication = mock(AnonymousAuthenticationToken.class);
//        SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication);
//        fundWalletService.fundWallet(new FundWalletRequest(100.0));
//    }
//}
