package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;

public interface CartService {
    String addItemToCart(Long productId, AddItemToCartDto addItemToCartDto);
    String removeItem(Long itemToRemoveId);
}



