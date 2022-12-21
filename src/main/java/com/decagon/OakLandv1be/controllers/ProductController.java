package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.services.serviceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("/view/{product_id}")
    public ResponseEntity<ProductCustResponseDto> viewASingleProduct(@PathVariable("product_id") Long product_id){
        return new ResponseEntity<>(productService.fetchASingleProduct(product_id), HttpStatus.OK);

    }
    @GetMapping("/view-all-products")
    public ResponseEntity<List<ProductCustResponseDto>> viewAllProducts(){
        List<ProductCustResponseDto> productCustResponseDtos = productService.fetchAllProducts();
        return new ResponseEntity<>(productCustResponseDtos, HttpStatus.OK);

    }
}
