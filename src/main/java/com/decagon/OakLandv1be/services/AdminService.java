package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.NewProductDto;
import com.decagon.OakLandv1be.dto.OperationStatus;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;

import javax.validation.Valid;

public interface AdminService {
    ProductResponseDto fetchASingleProduct(Long product_id);
    String deactivateUser(Long customerId);
    ApiResponse<NewProductDto> addNewProduct(@Valid NewProductDto productDto);
    ApiResponse<OperationStatus> deleteProduct(Long product_id);
    ApiResponse<Product> updateProduct(Long productId, UpdateProductDto updateproductDto);
}
