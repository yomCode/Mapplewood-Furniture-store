package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
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
    private final ResponseManager responseManager;

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable Long itemId){
       cartService.removeItem(itemId);
        return new ResponseEntity<>( responseManager.success("Item removed successfully"), HttpStatus.OK);
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
