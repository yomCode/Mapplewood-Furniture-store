package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item_tbl")
public class Item extends BaseEntity{

    private String productName;
    private String imageUrl;

    @NotBlank(message = "Kindly specify the quantity you desire, for this product")
    private Integer orderQty;
    private Double unitPrice;
    private Double subTotal;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    private Product product;
}
