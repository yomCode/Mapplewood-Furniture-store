package com.decagon.OakLandv1be.services;


import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.dto.UpdateProductDto;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductCustResponseDto fetchASingleProduct(Long product_id);

    
    List<ProductCustResponseDto> viewNewArrivalProducts();
  
    List<ProductCustResponseDto> viewBestSellingProducts();





    Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer page, Integer size, String sortingField,boolean isAscending);

    List<ProductCustResponseDto> fetchAllProducts();

    ApiResponse<Page<Product>> getAllProducts(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);

    String uploadProductImage(long productId, MultipartFile image) throws IOException;

    ApiResponse<Page<Product>> getAllProductsBySubCategory(Long subCategoryId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
}
