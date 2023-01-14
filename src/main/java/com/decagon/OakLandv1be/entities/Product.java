package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "product_tbl")
public class Product extends BaseEntity{

    private String name;

    private Double price;

    private String imageUrl;

    private Integer availableQty;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subCategory_id")
    private SubCategory subCategory;

    private String color;

    private String description;

    @JsonIgnore
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Item item;
}
