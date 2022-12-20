package com.decagon.OakLandv1be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {
    private String modeOfDelivery;
    private String modeOfPayment;
}
