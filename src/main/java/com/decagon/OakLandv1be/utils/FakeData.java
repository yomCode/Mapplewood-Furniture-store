package com.decagon.OakLandv1be.utils;

import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.PersonRepository;
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
    public CommandLineRunner commandLineRunner(PersonRepository personRepository) {
        return argument -> {
            if(!personRepository.existsByEmail("benson@gmail.com")) {
                Person person = Person.builder()
                        .firstName("Benson")
                        .lastName("Malik")
                        .email("benson@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .verificationStatus(true)
                        .password(passwordEncoder.encode("password123"))
                        .address("No Address")
                        .role(Role.ADMIN)
                        .build();
                personRepository.save(person);
            }
        };
    }
}
