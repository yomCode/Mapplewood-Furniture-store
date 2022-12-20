package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto fetchASingleProduct(Long product_id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if ((authentication instanceof AnonymousAuthenticationToken))
//            throw new ResourceNotFoundException("You need to login carry ut this operation");
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
            throw new AlreadyExistsException("Product with name: " +
                    newProductRequestDto.getName() + " already exists");

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
}
