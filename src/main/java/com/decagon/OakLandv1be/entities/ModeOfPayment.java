package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "modeOfPayment_tbl")
public class ModeOfPayment extends BaseEntity{

    private String name;

    private String provider;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}
