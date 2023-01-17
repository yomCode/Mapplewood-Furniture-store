package com.decagon.OakLandv1be.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCategoryDto {

    @NotNull(message = "Field cannot be missing or empty")
    private String name;
}
