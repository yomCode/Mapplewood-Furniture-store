//package com.decagon.OakLandv1be.services.serviceImpl;
//
//import com.decagon.OakLandv1be.dto.ProductResponseDto;
//import com.decagon.OakLandv1be.entities.Product;
//import com.decagon.OakLandv1be.repositries.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class AdminServiceImplTest {
//    @Mock
//    ProductRepository productRepository;
//
//    @InjectMocks
//    AdminServiceImpl adminService;
//
//    private Product product;
//    private ProductResponseDto productResponseDto;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        product = Product.builder().build();
//        productResponseDto = ProductResponseDto.builder().build();
//
//
//    }
//}