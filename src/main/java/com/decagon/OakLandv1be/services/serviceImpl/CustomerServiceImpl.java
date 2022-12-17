package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.InvalidTokenException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TokenRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import static com.decagon.OakLandv1be.enums.TokenStatus.ACTIVE;
import static com.decagon.OakLandv1be.enums.TokenStatus.EXPIRED;

@Service
@Data
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailService javaMailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final ResponseManager responseManager;


    @Override
    public SignupResponseDto saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
        // Checking database if email already exist
            boolean emailExist = personRepository.existsByEmail(signupRequestDto.getEmail());

            if (emailExist)
                throw new AlreadyExistsException("This Email address already exists");

            Customer customer = new Customer();

            Set<Address> addresses = new HashSet<>();
            Address address = Address.builder()
                    .fullName(signupRequestDto.getFirstName() + " " + signupRequestDto.getLastName())
                    .emailAddress(signupRequestDto.getEmail())
                    .state(signupRequestDto.getState())
                    .country(signupRequestDto.getCountry())
                    .street(signupRequestDto.getStreet())
                    .build();
            addresses.add(address);

            Person person = Person.builder()
                    .role(Role.CUSTOMER)
                    .verificationStatus(false)
                    .address(String.valueOf(address))
                    .customer(customer)
                    .email(signupRequestDto.getEmail())
                    .firstName(signupRequestDto.getFirstName())
                    .lastName(signupRequestDto.getLastName())
                    .phone(signupRequestDto.getPhoneNumber())
                    .gender(Gender.valueOf(signupRequestDto.getGender().toUpperCase()))
                    .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                    .date_of_birth(signupRequestDto.getDate_of_birth())
                    .customer(customer)
                    .build();

            Wallet wallet = Wallet.builder()
                    .baseCurrency(BaseCurrency.NAIRA)
                    .accountBalance(0.00)
                    .customer(customer)
                    .build();

            customer.setWallet(wallet);
            customer.setPerson(person);
            customer.setAddressBook(addresses);

            personRepository.save(person);
            walletRepository.save(wallet);
            customerRepository.save(customer);

            String validToken = tokenService.generateVerificationToken(signupRequestDto.getEmail());
            Token token = new Token();
            token.setToken(validToken);
            token.setTokenStatus(ACTIVE);
            token.setPerson(person);
            tokenRepository.save(token);

            javaMailService.sendMail(signupRequestDto.getEmail(),
                    "Verify your email address",
                    "Hi " + person.getFirstName() + " " + person.getLastName() + ", Thank you for your interest in joining Oakland." +
                            "To complete your registration, we need you to verify your email address " + "http://localhost:8080/api/v1/auth/customer/verifyRegistration/" + validToken);

            // use the user object to create UserResponseDto Object
            return SignupResponseDto.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .email(person.getEmail())
                    .gender(person.getGender())
                    .date_of_birth(person.getDate_of_birth())
                    .phone(person.getPhone())
                    .verificationStatus(person.getVerificationStatus())
                    .address(address)
                    .build();
    }
    @Override
    public ResponseEntity<ApiResponse> verifyRegistration(String token){

        Token verificationToken = tokenRepository.findByToken(token).orElseThrow(
                ()-> new InvalidTokenException("Token Not Found"));

        if(verificationToken.getTokenStatus().equals(EXPIRED))
            throw new InvalidTokenException("Token already used");

        verificationToken.getPerson().setVerificationStatus(true);
        verificationToken.setTokenStatus(EXPIRED);
        tokenRepository.save(verificationToken);
        return new ResponseEntity<>(responseManager.success("Account verification successful"), HttpStatus.OK);

    }



}