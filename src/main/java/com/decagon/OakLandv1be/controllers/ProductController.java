package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.services.serviceImpl.ProductServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/page-and-sort")
    public ResponseEntity<Page<ProductCustResponseDto>> productsByPaginationAndSorted(
            @RequestParam Integer offset, @RequestParam  Integer size, @RequestParam  String sortingField ){
        return  new ResponseEntity<>(productService.productWithPaginationAndSorting(offset, size, sortingField),HttpStatus.OK);
    }

    @PostMapping("/upload-image/{productId}")
    public ResponseEntity<Object> uploadProfilePic(@RequestPart MultipartFile productImage, @PathVariable Long productId) throws IOException {
        return ResponseEntity.ok(productService.uploadProductImage(productId, productImage));
    }

    @GetMapping("/new-arrival")
    public ResponseEntity<List<ProductCustResponseDto>> viewNewArrivals(){
        return new ResponseEntity<>(productService.viewNewArrivalProducts(), HttpStatus.OK);
    }

    @GetMapping("/best-selling")
    public ResponseEntity<List<ProductCustResponseDto>> viewBestSelling(){
        return new ResponseEntity<>(productService.viewBestSellingProducts(), HttpStatus.OK);
    }
}