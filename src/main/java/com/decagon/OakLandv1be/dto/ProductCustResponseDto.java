package com.decagon.OakLandv1be.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCustResponseDto {

    private String name;

    private Double price;

    private String imageUrl;

    private String color;

    private String description;
}
