package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutDto;
import com.decagon.OakLandv1be.dto.CheckoutResponseDto;
import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.services.CheckoutService;
import com.decagon.OakLandv1be.services.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Data
@RequestMapping("api/v1/customer")
public class OrderController {
    private final CheckoutService checkoutService;
    private final OrderService orderService;
    @PostMapping ("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkoutOrder(@Valid @RequestBody CheckoutDto checkoutDto){
        return checkoutService.cartCheckout(checkoutDto);

    }
    @PostMapping("/add-new-address")
    public ResponseEntity<String> addNewAddress(@Valid @RequestBody AddressRequestDto addressRequestDto){
        return checkoutService.addNewAddress(addressRequestDto);
    }

    @GetMapping("/order-history")
    public ResponseEntity<List<OrderResponseDto>> viewOrderHistory(@RequestParam int pageNo,
                                                                   @RequestParam int pageSize){
        return ResponseEntity.ok(orderService.viewOrderHistory(pageNo, pageSize));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponseDto> viewASingleOrder (@PathVariable("orderId") Long orderId){
       return new ResponseEntity<>(orderService.viewAParticularOrder(orderId), HttpStatus.OK);
    }
}