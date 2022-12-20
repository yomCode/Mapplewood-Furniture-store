package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public interface AdminService {
    ProductResponseDto fetchASingleProduct(Long product_id);

    ResponseEntity<Product> addNewProduct(@Valid NewProductRequestDto productDto);
    public void deactivateUser(Long customerId);

}
