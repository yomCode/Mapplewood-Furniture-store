package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;
    private Product product;
    private ProductCustResponseDto productCustResponseDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                .color("green").description("strong black").build();
        productCustResponseDto = ProductCustResponseDto.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                .color("green").description("strong black").build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
    }

    @Test
    void fetchASingleProduct() {
        Assertions.assertEquals(productCustResponseDto, productService.fetchASingleProduct(anyLong()));
    }
}