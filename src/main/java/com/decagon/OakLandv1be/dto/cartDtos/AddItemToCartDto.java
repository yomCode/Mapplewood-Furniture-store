package com.decagon.OakLandv1be.dto.cartDtos;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AddItemToCartDto {

    @NotBlank(message = "Kindly specify the quantity you desire, for this product")
    private Integer orderQty;
}
