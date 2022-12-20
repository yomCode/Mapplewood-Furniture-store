package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartResponseDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;

public interface CartService {
    CartResponseDto addItemToCart(Long productId, AddItemToCartDto addItemToCartDto) throws AlreadyExistsException;
//    Item getNewCartItem(int itemQuantity, Product product, Customer loggedInCustomer);
//    Cart getLoggedInCustomerCart(Customer loggedInCustomer, Set<Item> allCartItems);
}

