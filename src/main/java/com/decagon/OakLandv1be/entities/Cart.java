package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart_tbl")
public class Cart extends BaseEntity{

    @JsonIgnore
    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Item> items = new HashSet<>();

    private Double total;

    @JsonIgnore
    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Customer customer;
}
