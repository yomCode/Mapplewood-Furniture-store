package com.decagon.OakLandv1be.services.serviceImpl;

import com.cloudinary.utils.ObjectUtils;
import com.decagon.OakLandv1be.config.CloudinaryConfig;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.InvalidAttributeException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.Mapper;
import com.decagon.OakLandv1be.utils.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CloudinaryConfig cloudinaryConfig;

    public ProductCustResponseDto fetchASingleProduct(Long product_id) {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        return ProductCustResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .color(product.getColor())
                .description(product.getDescription())
                .subCategory(product.getSubCategory())
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
    public Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer page, Integer size, String sortingField) {

        page=page<0?0:page;

        size=size<10?10:size;

        if(!(sortingField.equalsIgnoreCase("name") || sortingField.equalsIgnoreCase("price")
        || sortingField.equalsIgnoreCase("color") || sortingField.equalsIgnoreCase("colour"))){
            sortingField="price";
        }
        if(sortingField.equalsIgnoreCase("colour")){
            sortingField="color";
        }

        return productRepository.findAll(PageRequest.of(page,size).withSort(Sort.by(sortingField)))
                .map(Mapper::productToProductResponseDto);
    }

    @Override
    public String uploadProductImage(long productId, MultipartFile image) throws IOException {
//        String email = userAuth.getPrincipal();
        Product product = productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product not found"));

        String productImageUrl = uploadImage(image);
        product.setImageUrl(productImageUrl);
        productRepository.save(product);
        return "Image uploaded successfully";
    }


    public String uploadImage(MultipartFile image) throws IOException {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(uploadedFile, ObjectUtils.asMap("use_filename", true, "unique_filename", true));
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted){
                log.info("File successfully deleted");
            }else
                log.info("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }


    private File convertMultiPartToFile(MultipartFile image) throws IOException {
        String file =  image.getOriginalFilename();
        if (file == null) throw new AssertionError();
        File convFile = new File(file);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(image.getBytes());
        fos.close();
        return convFile;
    }

}
