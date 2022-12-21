package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.AdminService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ProductRepository productRepository;
    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ProductResponseDto fetchASingleProduct(Long product_id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String email = authentication.getName();
//            personRepository.findByEmail(email)
//                    .orElseThrow(() -> new ResourceNotFoundException("Admin User not found"));
            Product product = productRepository.findById(product_id)
                    .orElseThrow(() -> new ProductNotFoundException("This product was not found"));
            return ProductResponseDto.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .availableQty(product.getAvailableQty())
                    .subCategory(product.getSubCategory())
                    .color(product.getColor())
                    .description(product.getDescription())
                    .build();
        }

    public ResponseEntity<Product> addNewProduct(NewProductRequestDto newProductRequestDto) {
        if(productRepository.existsByName(newProductRequestDto.getName()))
            throw new AlreadyExistsException("Product with name '" +
                    newProductRequestDto.getName() + "' already exists");

        Product product = Product.builder()
                .name(newProductRequestDto.getName())
                .price(newProductRequestDto.getPrice())
                .imageUrl(newProductRequestDto.getImageUrl())
                .availableQty(newProductRequestDto.getAvailableQty())
                .subCategory(newProductRequestDto.getSubCategory())
                .color(newProductRequestDto.getColor())
                .description(newProductRequestDto.getDescription())
                .build();

        Product newProduct = productRepository.save(product);


        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @Override
    public void deactivateUser(Long customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Person customer = personRepository.findById(customerId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        customer.setActive(false);
        personRepository.save(customer);
    }
    
    
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
