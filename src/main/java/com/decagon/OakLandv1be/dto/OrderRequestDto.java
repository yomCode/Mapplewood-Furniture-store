package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OrderRequestDto {
    private Set<Item> items;
    private Double grandTotal;
    private PickupCenter pickupCenter;
}
