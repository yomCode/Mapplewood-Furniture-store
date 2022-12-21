package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutItemDto;
import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.services.CheckoutService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Data
@RequestMapping("api/v1/customer")
public class OrderController {
    private final CheckoutService checkoutService;

    @GetMapping("/checkout")
    public ResponseEntity<Order> checkoutOrder(@RequestBody AddressRequestDto addressRequestDto, CheckoutItemDto checkoutItemDto){
        Order checkoutResponseDto=checkoutService.cartCheckout(addressRequestDto,checkoutItemDto);
        return new ResponseEntity<>(checkoutResponseDto, HttpStatus.OK);
    }
}