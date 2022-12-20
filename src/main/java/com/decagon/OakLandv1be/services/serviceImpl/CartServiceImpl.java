package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.NotAvailableException;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final ItemRepository itemRepository;

    @Override
    public CartResponseDto addItemToCart(Long productId, AddItemToCartDto addItemToCartDto) {

        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = cartRepository.findByCustomer(loggedInCustomer);

        if(cart == null){
            cart = new Cart();
            cart.setCustomer(loggedInCustomer);
        }

        cart.setCustomer(loggedInCustomer);

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotAvailableException("Product not available"));

        if(product.getAvailableQty() == 0)
            throw new NotAvailableException("Product out of stock");
        if(addItemToCartDto.getOrderQty() > product.getAvailableQty())
            throw new NotAvailableException("Requested quantity more than available quantity, for this product");

        Set<Item> allCartItems = cart.getItems();

        Item newCartItem = new Item();
        BeanUtils.copyProperties(product, newCartItem);
        newCartItem.setProductName(product.getName());
        newCartItem.setUnitPrice(product.getPrice());
        newCartItem.setOrderQty(addItemToCartDto.getOrderQty());
        newCartItem.setSubTotal(addItemToCartDto.getOrderQty() * product.getPrice());
        allCartItems.add(newCartItem);

        cart.setItems(allCartItems);

        Double cartTotal = 0.0;

        for(Item item : cart.getItems()){
            cartTotal += item.getSubTotal();
        }

        cart.setTotal(cartTotal);

        newCartItem.setCart(cart);
        itemRepository.save(newCartItem);

        CartResponseDto cartResponseDto = new CartResponseDto();
        BeanUtils.copyProperties(newCartItem, cartResponseDto);
        return cartResponseDto;
    }
}
