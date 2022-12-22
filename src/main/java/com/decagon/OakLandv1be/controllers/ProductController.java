package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.services.serviceImpl.ProductServiceImpl;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("/view/{product_id}")
    public ResponseEntity<ProductCustResponseDto> viewASingleProduct(@PathVariable("product_id") Long product_id){
        return new ResponseEntity<>(productService.fetchASingleProduct(product_id), HttpStatus.OK);

    }
    @GetMapping("/view-all-products")
    public ResponseEntity<List<ProductCustResponseDto>> viewAllProducts(){
        List<ProductCustResponseDto> productCustResponseDtos = productService.fetchAllProducts();
        return new ResponseEntity<>(productCustResponseDtos, HttpStatus.OK);

    }

    @GetMapping("/page-and-sort/{offset}/{size}/{sortingField}")
    public ResponseEntity<Page<ProductCustResponseDto>> productsByPaginationAndSorted(
            @PathVariable Integer offset, @PathVariable Integer size,@PathVariable String sortingField ){
        return  new ResponseEntity<>(productService.productWithPaginationAndSorting(offset, size, sortingField),HttpStatus.OK);
    }
}