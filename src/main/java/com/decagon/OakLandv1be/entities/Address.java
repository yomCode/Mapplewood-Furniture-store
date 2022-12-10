package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String fullName;
    private String emailAddress;
    private String street;
    private String state;
    private String country;

}
