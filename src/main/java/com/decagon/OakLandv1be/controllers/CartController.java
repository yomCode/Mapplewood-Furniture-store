package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer/cart")
public class CartController {
    private final CartService cartService;
    private final ItemRepository itemRepository;

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
       // cartService.removeItemInCart();
        return new ResponseEntity<>( cartService.removeItem(itemId), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/item/add-to-quantity/{itemId}")
    public ResponseEntity<String> addToItemQuantity(@PathVariable Long itemId){
        return new ResponseEntity<>(cartService.addToItemQuantity(itemId), HttpStatus.OK);
    }

    @PutMapping("/item/reduce-quantity/{itemId}")
    public ResponseEntity<String> reduceItemQuantity(@PathVariable Long itemId){
        return new ResponseEntity<>(cartService.reduceItemQuantity(itemId), HttpStatus.OK);
    }
}
