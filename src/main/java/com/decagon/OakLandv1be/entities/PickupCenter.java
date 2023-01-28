package com.decagon.OakLandv1be.entities;

import lombok.*;
import javax.persistence.*;

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
    private String email;
    private String phone;
    private Double delivery;

    @ManyToOne
    @JoinColumn(name = "states_id")
    private State state;

}
