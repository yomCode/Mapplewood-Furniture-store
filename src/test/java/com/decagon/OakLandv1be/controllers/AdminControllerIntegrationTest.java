package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.services.AdminService;
import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.ApiResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    private Person person;
    private Customer customer;
    private Product product, updatedProduct;
    private UpdateProductDto updateProductDto;

    private NewProductRequestDto newProductDto;
    private ProductResponseDto newProductResponseDto;

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


        product = Product.builder()
                .name("Oak Cupboard")
                .price(230_000D)
                .imageUrl("imageUrl")
                .availableQty(4)
                .subCategory(new SubCategory())
                .color("BROWN")
                .description("This is a valid description of the furniture.")
                .build();

        product.setId(23L);

        newProductDto = NewProductRequestDto.builder()
                .name("Oak Cupboard")
                .price(230_000D)
                .imageUrl("imageUrl")
                .availableQty(4)
                .subCategory(new SubCategory())
                .color("BROWN")
                .description("This is a valid description of the furniture.")
                .build();

        newProductResponseDto = ProductResponseDto.builder()
                .name("Oak Cupboard")
                .price(230_000D)
                .imageUrl("imageUrl")
                .availableQty(4)
                .subCategory(new SubCategory())
                .color("BROWN")
                .description("This is a valid description of the furniture.")

                .build();

        product = Product.builder()
                .name("center table")
                .price(20.0d)
                .imageUrl("")
                .availableQty(10)
                .subCategory(new SubCategory())
//                .customer(new Customer())
                .color("blue")
                .description("new products")
                .build();

        product.setId(23L);

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
        when(productRepository.save(product)).thenReturn(product);
       ResponseEntity<ProductResponseDto> apiResponse =
               new ResponseEntity<>(newProductResponseDto, HttpStatus.CREATED);

        assertEquals(product, productRepository.save(product));

        ResponseEntity<Product> testResponse = adminService.addNewProduct(newProductDto);
        when(productRepository.save(product)).thenReturn(product);

       assertEquals(apiResponse.getStatusCodeValue(), testResponse.getStatusCodeValue());
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