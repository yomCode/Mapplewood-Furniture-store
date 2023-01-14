package com.decagon.OakLandv1be.services.serviceImpl;


import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.InvalidTokenException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.*;

import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Set;
import static com.decagon.OakLandv1be.enums.TokenStatus.ACTIVE;
import static com.decagon.OakLandv1be.enums.TokenStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailService javaMailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final ResponseManager responseManager;

    private final ProductRepository productRepository;


    @Override
    public SignupResponseDto saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
            boolean emailExist = personRepository.existsByEmail(signupRequestDto.getEmail());
            if (emailExist)
                throw new AlreadyExistsException("This Email address already exists");

            Customer customer = new Customer();

            Person person = new Person();
        BeanUtils.copyProperties(signupRequestDto, person);
        person.setCustomer(customer);
        person.setRole(Role.CUSTOMER);
        person.setVerificationStatus(false);
        person.setGender(Gender.valueOf(signupRequestDto.getGender().toUpperCase()));
        person.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
            Wallet wallet = Wallet.builder()
                    .baseCurrency(BaseCurrency.NAIRA)
                    .accountBalance(0.00)
                    .customer(customer)
                    .build();

            customer.setWallet(wallet);
            customer.setPerson(person);

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
             SignupResponseDto signupResponseDto = new SignupResponseDto();
        BeanUtils.copyProperties(person, signupResponseDto);
                    return  signupResponseDto;
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

    @Override
    public void editProfile(EditProfileRequestDto editProfileRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Person customer = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        customer.setFirstName(editProfileRequestDto.getFirstName());
        customer.setLastName(editProfileRequestDto.getLastName());
        customer.setGender(Gender.valueOf(editProfileRequestDto.getGender().toUpperCase()));
        customer.setEmail(editProfileRequestDto.getEmail());
        customer.setPhone(editProfileRequestDto.getPhone());
        customer.setDate_of_birth(editProfileRequestDto.getDate_of_birth());

        personRepository.save(customer);
    }

    @Override
    public void addProductToFavorites(Long pid){
        Product product = productRepository.findById(pid).
                orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Product> favorites = person.getCustomer().getFavorites();
        if (favorites.contains(product)){
            throw new AlreadyExistsException("This product is already in favorites");
        }
        favorites.add(product);
        person.getCustomer().setFavorites(favorites);
        customerRepository.save(person.getCustomer());

    }

    @Override
    public Customer getCurrentlyLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();
        Person loggedInUser = personRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UserNotFoundException("No user with this email"));
        return loggedInUser.getCustomer();
    }
}
