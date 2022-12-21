package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductRequestDto {
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @DecimalMin(value="0.0", message="Field cannot be blank")
    private Double price;

    @NotBlank(message = "Field cannot be blank")
    private String imageUrl;

    @Range(min=0, message = "Field cannot be blank")
    private Integer availableQty;

    @NotNull(message="Field cannot be null")
    private SubCategory subCategory;

    @NotBlank(message = "Field cannot be blank")
    private String color;

    @NotBlank(message = "Field cannot be blank")
    private String description;

}
