package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;

public interface CartService {
    String addItemToCart(Long productId);
    String removeItem(Long itemToRemoveId);
    String addToItemQuantity(Long itemId);

    String reduceItemQuantity(Long itemId);
}



