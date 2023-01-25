package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartResponseDto;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Product;

import java.util.List;
import java.util.Set;

public interface CartService {
    String addItemToCart(Long productId, AddItemToCartDto addItemToCartDto);
    String removeItem(Long itemToRemoveId);
    List<CartItemResponseDto> fetchProductsFromCustomerCart();
}



