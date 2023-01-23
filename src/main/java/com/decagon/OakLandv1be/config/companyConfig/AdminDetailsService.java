package com.decagon.OakLandv1be.config.companyConfig;

import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SuperAdmin;
import com.decagon.OakLandv1be.entities.Wallet;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;

import com.decagon.OakLandv1be.exceptions.InvalidOperationException;

import com.decagon.OakLandv1be.repositries.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AdminDetailsService implements CommandLineRunner {
    private final ProductRepository productRepository;
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

            SuperAdmin superAdminDB=superAdminRepository.save(superAdmin);
            log.info("#############################################################################");
            log.info("Super Admin Details");

            log.info("Username/Email: \t"+password);
            log.info("Password/Passphrase: \t"+adminEmail);

            log.info("*******************   Wallet Information   *****************");
            log.info(superAdminDB.getWallet().toString());

            log.info("#############################################################################");


    }

    @Override
    public void run(String... args) throws Exception {
        initAdmin();

        Product[] products={Product.builder()
                .name("Oppola39")
                .price(50000.00)
                .availableQty(400)
                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                .color("yellow")
                .description("lovely fur")
                .build(),
                Product.builder()
                        .name("Oppola40")
                        .price(51000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola41")
                        .price(52000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola42")
                        .price(53000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola43")
                        .price(54000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola44")
                        .price(55000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola45")
                        .price(56000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),

                Product.builder()
                        .name("Oppola46")
                        .price(43000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola47")
                        .price(44000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola48")
                        .price(45000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build(),
                Product.builder()
                        .name("Oppola49")
                        .price(46000.00)
                        .availableQty(400)
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .color("yellow")
                        .description("lovely fur")
                        .build()
        };

        productRepository.saveAll(Arrays.asList(products))  ;
    }
}
