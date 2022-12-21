package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;

import java.util.List;

public interface ProductService {
    ProductCustResponseDto fetchASingleProduct(Long product_id);
    List<ProductCustResponseDto> fetchAllProducts();

}
