package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductCustResponseDto fetchASingleProduct(Long product_id) {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        return ProductCustResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .color(product.getColor())
                .description(product.getDescription())
                .build();
    }

    @Override
    public List<ProductCustResponseDto> fetchAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Products: " + products);
        List<ProductCustResponseDto> productCustResponseDtoList= new ArrayList<>();
        products.forEach(product -> {
            ProductCustResponseDto productCustResponseDto = new ProductCustResponseDto();
            productCustResponseDto.setName(product.getName());
            productCustResponseDto.setPrice(product.getPrice());
            productCustResponseDto.setImageUrl(product.getImageUrl());
            productCustResponseDto.setColor(product.getColor());
            productCustResponseDto.setDescription(product.getDescription());
            productCustResponseDtoList.add(productCustResponseDto);
        });
        return productCustResponseDtoList;
    }


    public ResponseEntity<Boolean> deleteProduct(Long id){


        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);

        Product removedProduct = productRepository.findById(id).orElse(null);

        if(removedProduct == null)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

    }



    @Override
    public Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer offset, Integer size, String field) {
        if(size<1) size=10;
        if(offset<1) offset=1;
//        if(field.toLowerCase()!="name"|| field.toLowerCase()!="color"|| field.toLowerCase()!="description"){
//            throw new InvalidAttributeException("Invalid product" +
//                " attribute");}
        return productRepository.findAll(PageRequest.of(offset,size).withSort(Sort.by(field)))
                .map(Mapper::productToProductResponseDto);
    }

}
