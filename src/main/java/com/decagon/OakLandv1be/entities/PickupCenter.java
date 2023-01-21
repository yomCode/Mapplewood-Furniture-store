package com.decagon.OakLandv1be.entities;

import com.decagon.OakLandv1be.enums.State;
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

    @Enumerated(EnumType.STRING)
    private State state;
    private String email;

    private String phone;

}
