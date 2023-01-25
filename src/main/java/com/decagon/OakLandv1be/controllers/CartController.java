package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final ResponseManager responseManager;

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId){
       cartService.removeItem(itemId);
        return new ResponseEntity<>( responseManager.success("Item removed successfully"), HttpStatus.OK);
    }

}
