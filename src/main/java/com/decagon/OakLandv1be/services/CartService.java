package com.decagon.OakLandv1be.services;

<<<<<<< HEAD
import com.decagon.OakLandv1be.dto.ProductResponseDto;
=======
import com.decagon.OakLandv1be.dto.CartDto;
>>>>>>> 92b8b81bf554bc02ad1a2546719430d7d767d43a
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
<<<<<<< HEAD
    List<CartItemResponseDto> fetchProductsFromCustomerCart();
=======
    CartDto viewCartByCustomer ();
>>>>>>> 92b8b81bf554bc02ad1a2546719430d7d767d43a
}



