package com.decagon.OakLandv1be.entities;


import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery extends BaseEntity{

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;


}
