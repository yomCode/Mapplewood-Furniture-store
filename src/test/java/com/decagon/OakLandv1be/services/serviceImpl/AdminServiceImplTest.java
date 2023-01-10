package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.OperationStatus;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    AdminServiceImpl adminService;

    private Product product;
    private ProductResponseDto productResponseDto;
    private SubCategory subCategory;

    private OperationStatus operationStatus;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subCategory = new SubCategory();
        product = Product.builder()
                .name("Tall dinning chair")
                .price(2000.00)
                .imageUrl("hgdhg")
                .availableQty(3)
                .subCategory(subCategory)
                .color("green")
                .description("strong black").build();
        productResponseDto = ProductResponseDto.builder()
                .name("Tall dinning chair")
                .price(2000.00)
                .imageUrl("hgdhg")
                .availableQty(3)
                .subCategory(subCategory)
                .color("green")
                .description("strong black").build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
    }

    @Test
    void fetchASingleProductShouldReturnProduct() {
        Assertions.assertEquals(productResponseDto, adminService.fetchASingleProduct(anyLong()));
    }

    @Test
    void testDeleteProductById() {
      adminService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(anyLong());
    }
}
