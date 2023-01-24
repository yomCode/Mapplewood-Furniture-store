package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.CartDto;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
       // cartService.removeItemInCart();
        return new ResponseEntity<>( cartService.removeItem(itemId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/view")
    public ResponseEntity<CartDto> viewCartByCustomer (){
        return new ResponseEntity<>(cartService.viewCartByCustomer(), HttpStatus.OK);
    }
}
