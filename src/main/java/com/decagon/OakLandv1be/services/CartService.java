package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.entities.Cart;

public interface CartService {
    Cart removeItemInCart();
    void removeItem(Long itemId);
}
