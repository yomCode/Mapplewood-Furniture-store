package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CheckoutResponseDto {
    private ModeOfPayment modeOfPayment;

    private Set<Item> items;

    private Double deliveryFee;

    private ModeOfDelivery modeOfDelivery;

    private Delivery delivery;

    private Double grandTotal;

    private Double discount;

    private Address address;

    private Customer customer;

}
