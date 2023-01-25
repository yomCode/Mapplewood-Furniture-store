package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer/cart")
public class CartController {
    private final CartService cartService;
    private final ResponseManager responseManager;

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable Long itemId){
       cartService.removeItem(itemId);
        return new ResponseEntity<>( responseManager.success("Item removed successfully"), HttpStatus.OK);
    }

    @PutMapping("/item/add-to-quantity/{productId}")
    public ResponseEntity<String> addToItemQuantity(@PathVariable Long productId){
        return new ResponseEntity<>(cartService.addToItemQuantity(productId), HttpStatus.OK);
    }

    @PutMapping("/item/reduce-quantity/{itemId}")
    public ResponseEntity<String> reduceItemQuantity(@PathVariable Long itemId){
        return new ResponseEntity<>(cartService.reduceItemQuantity(itemId), HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(){
        return new ResponseEntity<>(cartService.clearCart(), HttpStatus.OK);
    }

    @GetMapping("/items/all")
    public ResponseEntity<List<Item>> getAllCartItems(){
        return new ResponseEntity<>(cartService.getAllCartItems(), HttpStatus.OK);
    }
}
