package com.decagon.OakLandv1be.config.companyConfig;

import com.decagon.OakLandv1be.dto.SuperAdminDetailResponse;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.SuperAdmin;
import com.decagon.OakLandv1be.entities.Wallet;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;

import com.decagon.OakLandv1be.exceptions.InvalidOperationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AdminDetailsService implements CommandLineRunner {
    private final SuperAdminRepository superAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.currency}")
    private String currency;
    String password=UUID.randomUUID().toString();




    public void initAdmin(){

            if(superAdminRepository.findAll().size()>0){
                throw new InvalidOperationException("Operation NOT ALLOWED");
            }


            Person person= Person.builder()
                    .date_of_birth(new Date().toString())
                    .email(adminEmail)
                    .firstName("super")
                    .lastName("Admin")
                    .role(Role.SUPERADMIN)
                    .gender(Gender.OTHER)
                    .isActive(true)
                    .password(passwordEncoder.encode(password))
                    .verificationStatus(true)
                    .build();

            Wallet wallet= Wallet.builder()
                    .accountBalance(BigDecimal.ZERO)
                    .baseCurrency(BaseCurrency.valueOf(currency))
                    .build();
            SuperAdmin superAdmin= SuperAdmin.builder()
                    .person(person)
                    .wallet(wallet)
                    .build();

            superAdmin=superAdminRepository.save(superAdmin);
            log.info("#############################################################################");
            log.info("Super Admin Details");

            log.info("Username/Email: \t"+password);
            log.info("Password/Passphrase: \t"+adminEmail);

            log.info("*******************   Wallet Information   *****************");
            log.info(superAdmin.getWallet().toString());

            log.info("#############################################################################");


    }

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
    }
}
