package com.decagon.OakLandv1be.services;


import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.entities.Cart;

import java.util.List;
import java.util.Set;

public interface CartService {
    CartItemResponseDto addItemToCart(Long productId, AddItemToCartDto addItemToCartDto) throws AlreadyExistsException;
    Item getNewCartItem(int itemQuantity, Product product, Customer loggedInCustomer, List<Item> cartItemsList);
    Cart getLoggedInCustomerCart(Customer loggedInCustomer, List<Item> cartItemsList, Set<Item> allCartItems);
    String currentUserEmail();
}


  

public interface CartService {
    String removeItem(Long itemToRemoveId);
}

