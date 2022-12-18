package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;

public interface AdminService {
    ProductResponseDto fetchASingleProduct(Long product_id);
}
