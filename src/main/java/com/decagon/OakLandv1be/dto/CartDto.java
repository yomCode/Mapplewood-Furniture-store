package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Item;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {
    private Set<Item> items;
    private Double total;
}
