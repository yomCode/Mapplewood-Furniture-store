package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.services.JavaMailService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@Data
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailService javaMailService;


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

            javaMailService.sendMail(signupRequestDto.getEmail(),
                    "Verify your email address",
                    "Hi " + person.getFirstName() + " " + person.getLastName() + ", Thank you for your interest in joining Oakland." +
                            "To complete your registration, we need you to verify your email address");

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
}