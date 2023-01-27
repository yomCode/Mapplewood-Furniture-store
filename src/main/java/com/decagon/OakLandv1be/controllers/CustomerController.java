package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;

import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Item;
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
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ResponseManager responseManager;
    private final CustomerService customerService;
    private final CartService cartService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
        customerService.saveCustomer(signupRequestDto);
        return new ResponseEntity<>(responseManager.success("Registration Successful! Check your mail for activation link"),HttpStatus.CREATED);
    }
    @GetMapping("/verifyRegistration/{token}")
    public ResponseEntity<ApiResponse> verifyAccount(@PathVariable String token){
        return customerService.verifyRegistration(token);
    }
    
    @PutMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@Valid @RequestBody EditProfileRequestDto editProfileRequestDto){
        customerService.editProfile(editProfileRequestDto);
        return new ResponseEntity<>("Profile Updated Successfully", HttpStatus.OK);
    }


    @GetMapping("/view-profile")
    public ResponseEntity<CustomerProfileDto> viewProfile (){
        return new ResponseEntity<>(customerService.viewProfile(), HttpStatus.OK);
    }

    @GetMapping("/admin/customers-profile/page-sort")
    public ResponseEntity<Page<CustomerProfileDto>> viewAllProfilesPaginationAndSort(@Valid @RequestParam Integer pageNumber,
                                                                                     @RequestParam Integer pageSize,
                                                                                     @RequestParam String sortBy){
        return new ResponseEntity<>(customerService.viewAllCustomersProfileWithPaginationSorting(pageNumber, pageSize, sortBy),
                HttpStatus.OK);
    }

    @PostMapping("/cart/item/add/{productId}")
    public ResponseEntity<String> addItemToCart(@Valid @RequestBody AddItemToCartDto addItemToCartDto, @PathVariable Long productId) throws AlreadyExistsException {
        String response = cartService.addItemToCart(productId,addItemToCartDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/product/favorites/add/{pid}")
    public ResponseEntity<String> addFavorites(@PathVariable Long pid){
        customerService.addProductToFavorites(pid);
        return new ResponseEntity<>("Product added to favourites successfully", HttpStatus.ACCEPTED);

    }

    @GetMapping("/cart/item/view-all")
    public ResponseEntity<List<CartItemResponseDto>> fetchProductsFromCustomerCart() {
        List<CartItemResponseDto> items = cartService.fetchProductsFromCustomerCart();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
