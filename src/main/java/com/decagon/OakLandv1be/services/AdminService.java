package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.*;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;

import javax.validation.Valid;
import java.io.IOException;

public interface AdminService {
    ProductResponseDto fetchASingleProduct(Long product_id);
    String deactivateUser(Long customerId);
    ApiResponse<ProductResponseDto> addNewProduct(@Valid NewProductRequestDto productDto);
    void deleteProduct(Long product_id);
    ApiResponse<Product> updateProduct(Long productId, UpdateProductDto updateproductDto);


    AdminResponseDto createAdmin(AdminRequestDto adminRequestDto) throws IOException;
}
