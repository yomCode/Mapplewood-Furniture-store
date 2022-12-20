package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/products/{product_id}")
    public ResponseEntity<ProductResponseDto> viewASingleProduct(@PathVariable("product_id") Long product_id){
        return new ResponseEntity<>(adminService.fetchASingleProduct(product_id), HttpStatus.OK);

    }

    @PostMapping("products/new")
    ResponseEntity<Product> addNewProduct(@Valid @RequestBody NewProductRequestDto productDto) {
        return adminService.addNewProduct(productDto);
    }

}
