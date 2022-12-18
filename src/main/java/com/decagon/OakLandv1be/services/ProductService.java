package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;

public interface ProductService {
    ProductCustResponseDto fetchASingleProduct(Long product_id);
}
