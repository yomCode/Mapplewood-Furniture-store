package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutDto;
import com.decagon.OakLandv1be.dto.CheckoutResponseDto;
import com.decagon.OakLandv1be.services.CheckoutService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Data
@RequestMapping("api/v1/customer")
public class OrderController {
    private final CheckoutService checkoutService;
    @PostMapping ("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkoutOrder(@RequestBody CheckoutDto checkoutDto){
        return checkoutService.cartCheckout(checkoutDto);

    }
    @PostMapping("/add-new-address")
    public ResponseEntity<String> addNewAddress(@RequestBody AddressRequestDto addressRequestDto){
        return checkoutService.addNewAddress(addressRequestDto);
    }
}