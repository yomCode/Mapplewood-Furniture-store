package com.decagon.OakLandv1be.entities;

import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends BaseEntity{

    private String firstName;
    private String lastName;
    private String Email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String date_of_birth;
    private String phone;
    private Boolean verificationStatus;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Admin admin;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Customer customer;

}
