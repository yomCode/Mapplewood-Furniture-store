package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ApiResponse<Product> updateProduct(Long productId, UpdateProductDto updateproductDto) {
        Product product = productRepository.findById(productId).
                orElseThrow(()->
                        new ProductNotFoundException("Product does not exist"));

        product.setName(updateproductDto.getName());
        product.setPrice(updateproductDto.getPrice());
        product.setImageUrl(updateproductDto.getImageUrl());
        product.setAvailableQty(updateproductDto.getAvailableQty());
        product.setSubCategory(updateproductDto.getSubCategory());
        product.setColor(updateproductDto.getColor());
        product.setDescription(updateproductDto.getDescription());

        Product updatedProduct = productRepository.save(product);
        return new ApiResponse<>("product updated", true, updatedProduct);

    }

}
