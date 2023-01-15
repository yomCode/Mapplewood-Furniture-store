package com.decagon.OakLandv1be.utils;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class FakeData {

    private final PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner commandLineRunner(PersonRepository personRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        return argument -> {
            if (!personRepository.existsByEmail("benson@gmail.com")) {
             Customer customer = new Customer();
             
                Person person = Person.builder()
                        .firstName("Benson")
                        .lastName("Malik")
                        .email("bennyson1@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .isActive(true)
                        .verificationStatus(true)
                        .password(passwordEncoder.encode("password123"))
                        .address("No Address")
                        .role(Role.CUSTOMER)
                        .customer(customer)
                        .password(passwordEncoder.encode("password123453"))
                        .address("No Address")
                        .role(Role.ADMIN)
                        .isActive(true)
                        .build();
                personRepository.save(person);

                customer.setPerson(person);
                customerRepository.save(customer);
            }

            if(!productRepository.existsById(1L)) {
                Product product = Product.builder()
                        .name("Oppola")
                        .price(40000.00)
                        .availableQty(400)
                        .imageUrl("www.google.com")
                        .color("yellow")
                        .description("lovely fur")
                        .build();
                    product.setId(1L);
                productRepository.save(product);
            }

        };
    }

    public CommandLineRunner commandLineRunner(PersonRepository personRepository, CustomerRepository customerRepository) {
        return args -> {
            Person person = Person.builder()
                    .firstName("Maggie")
                    .lastName("Stubborn")
                    .password(passwordEncoder.encode("password123"))
                    .email("maggie@gmail.com")
                    .gender(Gender.OTHER)
                    .date_of_birth("12-09-1993")
                    .phone("78573944843")
                    .verificationStatus(true)
                    .address("Foolish address")
                    .role(Role.ADMIN)
                    .build();


            Customer customer = Customer.builder()
                    .person(person)
                    .cart(new Cart())
                    .wallet(Wallet.builder()
                            .accountBalance(4000D)
                            .baseCurrency(BaseCurrency.POUNDS)
                            .build())
                    .build();

            personRepository.save(person);
            customerRepository.save(customer);
        };
    }
}


