package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.SubCategory;
import lombok.Builder;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
public class ProductResponseDto {

    private String name;

    private Double price;

    private String imageUrl;

    private Integer availableQty;

    private SubCategory subCategory;

    private String color;

    private String description;
}
