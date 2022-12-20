package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ProductService productService;

 @PutMapping("/products/update/{productId}")
    public ApiResponse<Product> updateProduct(@Valid @PathVariable Long productId , @RequestBody UpdateProductDto updateproductDto) {
     return productService.updateProduct( productId, updateproductDto);
 }
}
