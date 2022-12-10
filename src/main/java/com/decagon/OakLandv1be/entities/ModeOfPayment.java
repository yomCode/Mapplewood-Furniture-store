package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModeOfPayment extends BaseEntity{

    private String name;

    private String provider;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
}
