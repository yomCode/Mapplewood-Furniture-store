package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.NewProductDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.repositries.CategoryRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.repositries.SubCategoryRepository;
import com.decagon.OakLandv1be.services.AdminService;
import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AdminControllerIntegrationTest {

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    SubCategoryRepository subCategoryRepository;
    @MockBean
    CategoryRepository categoryRepository;
    private Person person;
    private Customer customer;
    private Product product, updatedProduct;
    Category category;
    SubCategory subCategory;
    private UpdateProductDto updateProductDto;

    private NewProductDto newProductDto;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .firstName("Bernard")
                .lastName("Malik")
                .email("bernard@gmail.com")
                .gender(Gender.MALE)
                .date_of_birth("10-09-1990")
                .phone("08140874632")
                .verificationStatus(true)
                .password(passwordEncoder.encode("password123"))
                .role(Role.ADMIN)
                .build();

        person.setId(1L);

        customer = Customer.builder()
                .person(person)
                .cart(null)
                .wallet(null)
                .build();

        category = Category.builder()
                .name("Table")
                .build();
        subCategory = SubCategory.builder()
                .name("Coffee Table")
                .category(category)
                .build();

        product = Product.builder()
                .name("Oak Cupboard")
                .price(230_000D)
                .imageUrl("imageUrl")
                .availableQty(4)
                .subCategory(subCategory)
                .color("BROWN")
                .description("This is a valid description of the furniture.")
                .build();

        newProductDto = NewProductDto.builder()
                .name("Oak Cupboard")
                .price(230_000D)
                .imageUrl("imageUrl")
                .availableQty(4)
                .subCategory("new SubCategory()")
                .color("BROWN")
                .description("This is a valid description of the furniture.")
                .build();

        updateProductDto = UpdateProductDto.builder()
                .name("Not a center table")
                .price(20.0d)
                .imageUrl("blah blah")
                .availableQty(20)
                .subCategory(new SubCategory())
                .color("brown")
                .description("old products")
                .build();


        updatedProduct = Product.builder()
                .name("Not a center table")
                .price(20.0d)
                .imageUrl("blah blah")
                .availableQty(20)
                .subCategory(new SubCategory())
//                .customer(new Customer())
                .color("brown")
                .description("old products")
                .build();
    }

    @Test
    void addNewProduct() {
        when(productRepository.existsByName(any())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);

        when(productRepository.save(any())).thenReturn(product);

        ApiResponse<NewProductDto> apiResponse = adminService.addNewProduct(newProductDto);
        log.info("apiResponse: {}", apiResponse);
        assertNotNull(apiResponse.getData());
    }

    @Test
    void addNewProductShouldReturnNameAlreadyExists() {
        when(productRepository.save(any())).thenReturn(product);

        when(productRepository.existsByName(any())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> adminService.addNewProduct(newProductDto));
    }


    @Test
    void updateProduct() {
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product, productRepository.save(product));

        when(productRepository.findById(any()))
                .thenReturn(Optional.of(product));
        ApiResponse<Product> expectedApiResponse =
                new ApiResponse<>("product updated", true, updatedProduct);

        ApiResponse<Product> apiResponse1 =
                adminService.updateProduct(32l, updateProductDto);
        assertEquals(apiResponse1.toString(), expectedApiResponse.toString());

    }

    @Test
    void shouldThrowProductNotFoundException() {
        when(productRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> adminService.updateProduct(23L, updateProductDto));

    }

}