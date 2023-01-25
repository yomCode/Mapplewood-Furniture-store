package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.entities.Item;

import java.util.List;

public interface CartService {
    String addItemToCart(Long productId);
    String removeItem(Long itemToRemoveId);
    String addToItemQuantity(Long itemId);

    String reduceItemQuantity(Long itemId);

    String clearCart();

    List<Item> getAllCartItems();
}



