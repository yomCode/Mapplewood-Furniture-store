package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "address_tbl")
public class Address extends BaseEntity{

    private String fullName;
    private String emailAddress;
    private String street;
    private String state;
    private String country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Order order;
}
