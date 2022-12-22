package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
