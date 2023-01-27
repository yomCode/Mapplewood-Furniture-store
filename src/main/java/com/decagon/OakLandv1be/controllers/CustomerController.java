package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {
    private final ResponseManager responseManager;
    private final CustomerService customerService;
    private final CartService cartService;

    @PostMapping("/customer/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
        customerService.saveCustomer(signupRequestDto);
        return new ResponseEntity<>(responseManager.success("Registration Successful! Check your mail for activation link"),HttpStatus.CREATED);
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestParam("token") String token){
        return customerService.verifyRegistration(token);
    }
    
    @PutMapping("/customer/edit-profile")
    public ResponseEntity<String> editProfile(@Valid @RequestBody EditProfileRequestDto editProfileRequestDto){
        customerService.editProfile(editProfileRequestDto);
        return new ResponseEntity<>("Profile Updated Successfully", HttpStatus.OK);
    }


    @GetMapping("/customer/view-profile")
    public ResponseEntity<CustomerProfileDto> viewProfile (){
        return new ResponseEntity<>(customerService.viewProfile(), HttpStatus.OK);
    }

    @GetMapping("/admin/customers-profile/page-sort")
    public ApiResponse<Page<CustomerProfileDto>> viewAllProfilesPaginationAndSort(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "16") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy){
        return new ApiResponse<>("Paginated" ,
                customerService.viewAllCustomersProfileWithPaginationSorting(pageNo, pageSize, sortBy),
                HttpStatus.OK);
    }

    @PostMapping("/cart/item/add/{productId}")
    public ResponseEntity<String> addItemToCart(@PathVariable Long productId) throws AlreadyExistsException {
        String response = cartService.addItemToCart(productId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/customer/product/favorites/add/{pid}")
    public ResponseEntity<String> addFavorites(@PathVariable Long pid){
        customerService.addProductToFavorites(pid);
        return new ResponseEntity<>("Product added to favourites successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/favorite/view/{product_id}")
    public ResponseEntity<ProductCustResponseDto> viewASingleFavorite(@PathVariable Long product_id) {
        return new ResponseEntity<>(customerService.viewASingleFavorite(product_id), HttpStatus.OK);
    }
    @GetMapping("/product/favorites/viewAllFavorites")
    public ResponseEntity<Page<ProductCustResponseDto>> viewFavouritesByPagination(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                    @RequestParam(defaultValue = "16") Integer pageSize,
                                                                                    @RequestParam(defaultValue = "id") String sortBy){
        return new ResponseEntity<>(customerService.viewFavouritesByPagination(pageNo, pageSize, sortBy),HttpStatus.OK);

    }

}