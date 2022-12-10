package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PickupCenter extends BaseEntity{

    private String name;
    @Embedded
    private Address address;
    private String phone;

}
