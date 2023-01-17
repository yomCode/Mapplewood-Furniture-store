package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "state_tbl")
public class State extends BaseEntity{
    private String name;
    private String abbreviation;
    @OneToMany
    private Set<PickupCenter> pickupCenters;
}
