package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.OperationStatus;
import com.decagon.OakLandv1be.dto.ProductResponseDto;

import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.enums.OperationName;
import com.decagon.OakLandv1be.enums.OperationResult;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;

import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.AdminService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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
    public ApiResponse<OperationStatus> deleteProduct(Long product_id) {
        ResponseManager<OperationStatus> manager = new ResponseManager<>();
        Product product = productRepository.findById(product_id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        productRepository.deleteById(product_id);
        return manager.success(new OperationStatus(OperationName.DELETE.name(), OperationResult.SUCCESS.name()));
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
