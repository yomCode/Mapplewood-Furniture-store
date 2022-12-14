package com.decagon.OakLandv1be.entities;

import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "person_tbl")
public class Person extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String date_of_birth;
    private String phone;
    private Boolean verificationStatus;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Admin admin;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Customer customer;

}
