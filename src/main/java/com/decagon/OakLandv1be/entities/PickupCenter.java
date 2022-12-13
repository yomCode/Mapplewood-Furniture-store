package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pickupCenter_tbl")
public class PickupCenter extends BaseEntity{

    private String name;

    private String address;

    private String phone;

}
