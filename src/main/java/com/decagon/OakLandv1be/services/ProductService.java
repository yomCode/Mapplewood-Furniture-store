package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.UpdateProductDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductCustResponseDto fetchASingleProduct(Long product_id);

    public Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer offset, Integer size, String field);
    
    Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer page, Integer size, String sortingField,boolean isAscending);

    public List<ProductCustResponseDto> fetchAllProducts();

    String uploadProductImage(long productId, MultipartFile image) throws IOException;
}
