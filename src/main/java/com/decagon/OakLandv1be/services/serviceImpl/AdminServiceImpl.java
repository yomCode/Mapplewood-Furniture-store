package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.dto.*;

import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.PickupCenter;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.*;

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
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final PickupRepository pickupRepository;

    @Override
    public ProductResponseDto fetchASingleProduct(Long product_id) {
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

    @Override
    public ApiResponse<ProductResponseDto> addNewProduct(NewProductRequestDto newProductRequestDto) {
        if(productRepository.existsByName(newProductRequestDto.getName()))
            throw new AlreadyExistsException("Product with name '" +
                    newProductRequestDto.getName() + "' already exists");

        SubCategory subCategory = subCategoryRepository
                .findByName(newProductRequestDto.getSubCategory())
                .orElseThrow(() ->
                        new ProductNotFoundException("SubCategory does not exist"));

        Product product = Product.builder()
                .name(newProductRequestDto.getName())
                .price(newProductRequestDto.getPrice())
                .imageUrl(newProductRequestDto.getImageUrl())
                .availableQty(newProductRequestDto.getAvailableQty())
                .subCategory(subCategory)
                .color(newProductRequestDto.getColor())
                .description(newProductRequestDto.getDescription())
                .build();

        Product newProduct = productRepository.save(product);
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .name(newProduct.getName())
                .price(newProduct.getPrice())
                .availableQty(newProduct.getAvailableQty())
                .subCategory(SubCategory.builder()
                        .name(subCategory.getName())
                        .category(subCategory.getCategory())
                        .build())
                .imageUrl(newProduct.getImageUrl())
                .color(newProduct.getColor())
                .description(newProduct.getDescription())
                .build();
        return new ApiResponse<>("Product Added", productResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void deleteProduct(Long product_id) {
        Product product = productRepository.findById(product_id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);

    }

    @Override
    public String deactivateUser(Long customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Person customer = personRepository.findById(customerId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        boolean isActive = !customer.isActive();
        customer.setActive(isActive);
        personRepository.save(customer);
        return isActive ? "Account Re-activated":"Account deactivated";
    }
    
    @Override
    public ApiResponse<Product> updateProduct(Long productId, UpdateProductDto updateproductDto) {
        Product product = productRepository.findById(productId).
                orElseThrow(()->
                        new ProductNotFoundException("Product does not exist"));

        SubCategory subCategory = subCategoryRepository
                .findByName(updateproductDto.getSubCategory())
                .orElseThrow(() ->
                        new ProductNotFoundException("SubCategory does not exist"));


        product.setName(updateproductDto.getName());
        product.setPrice(updateproductDto.getPrice());
        product.setImageUrl(updateproductDto.getImageUrl());
        product.setAvailableQty(updateproductDto.getAvailableQty());
        product.setSubCategory(subCategory);
        product.setColor(updateproductDto.getColor());
        product.setDescription(updateproductDto.getDescription());

        Product updatedProduct = productRepository.save(product);
        return new ApiResponse<>("product updated", true, updatedProduct);
    }

    @Override
    public PickupCenter updatePickupCenter(Long pickupCenterId, UpdatePickUpCenterDto request) {
        PickupCenter center = pickupRepository.findById(pickupCenterId).
                orElseThrow(()-> new PickupCenterNotFoundException("Pickup Center does not exist"));

        center.setName(request.getName());
        center.setAddress(request.getAddress());
        center.setEmail(request.getEmail());
        center.setPhone(request.getPhone());

        PickupCenter pickupCenter= pickupRepository.save(center);
        return pickupCenter ;
    }


}
