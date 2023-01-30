package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.*;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import com.decagon.OakLandv1be.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

   @PutMapping("/products/update/{productId}")
      public ApiResponse<Product> updateProduct(@Valid @PathVariable Long productId , @RequestBody UpdateProductDto updateproductDto) {
       return adminService.updateProduct( productId, updateproductDto);
   }

    @GetMapping("/products/{product_id}")
    public ResponseEntity<ProductResponseDto> viewASingleProduct(@PathVariable("product_id") Long product_id){
        return new ResponseEntity<>(adminService.fetchASingleProduct(product_id), HttpStatus.OK);

    }

    @PostMapping("/products/new")
    ApiResponse<ProductResponseDto> addNewProduct(@Valid @RequestBody NewProductRequestDto productDto) {
        return adminService.addNewProduct(productDto);
    }
    
    @PutMapping("/deactivate-user/{userId}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId){
        String response = adminService.deactivateUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/addresses/{customerId}")
    public ResponseEntity<Set<AddressResponseDto>> viewAllCustomerAddress(@PathVariable Long customerId){
       Set<AddressResponseDto> response = adminService.viewAllCustomerAddress(customerId);
       return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/delete/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long product_id){
       adminService.deleteProduct(product_id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
