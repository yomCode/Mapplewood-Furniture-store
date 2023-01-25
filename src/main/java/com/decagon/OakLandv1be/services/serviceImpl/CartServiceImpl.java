package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.CartDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.NotAvailableException;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TokenRepository;
import com.decagon.OakLandv1be.services.CustomerService;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CartRepository cartRepository;
    private final TokenRepository tokenRepository;
    private final ItemRepository itemRepository;


    @Override
    public String addItemToCart(Long productId, AddItemToCartDto addItemToCartDto) {

        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotAvailableException("Product" +
                " not available"));

        if (product.getAvailableQty() == 0)
            throw new NotAvailableException("Product out of stock");
        if (addItemToCartDto.getOrderQty() > product.getAvailableQty())
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

        for (Item item : cart.getItems()) {
            cartTotal += item.getSubTotal();
        }

        cart.setTotal(cartTotal);

        customerRepository.save(loggedInCustomer);

        return "Item Saved to Cart Successfully";
    }


    @Override
    public String removeItem(Long itemToRemoveId) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Cart cart = person.getCustomer().getCart();
            if (cart == null) throw new ResourceNotFoundException("cart is empty");

            Item item = itemRepository.findById(itemToRemoveId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
            Set<Item> itemsInCart = cart.getItems();
            // for (Item item : itemsInCart) {
            //if (item.getId() == itemToRemoveId) {
            if (itemsInCart.contains(item))
//                    //    if(itemsInCart.contains(item))
////            itemsInCart.remove(item);
////        cart.setItems(itemsInCart);
////        itemRepository.save(item);
                itemRepository.delete(item);

            return "item removed successfully";

//                    //search for the logged in user
//                    //get his cart
//                    //cart
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (!(authentication instanceof AnonymousAuthenticationToken)) {
////            String email = authentication.getName();
////
////            Person person = personRepository.findByEmail(email)
////                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
////
////            Cart cart = person.getCustomer().getCart();
////            if( cart == null) throw new ResourceNotFoundException("cart is empty");
////            Item item = itemRepository.findById(itemToRemoveId)
////                    .orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
////            itemRepository.delete(item);
////            return "Item successfully deleted";
////
////            System.out.println(" person is " + person.getCustomer());
////            Set<Item> itemsInCart = cart.getItems();
////            for (Item item : itemsInCart) {
////                if (item.getId() == itemToRemoveId) {
////                    itemsInCart.remove(item);
////
////                    cart.setItems(itemsInCart);
////                    cartRepository.save(cart);
////                    personRepository.save(person);
////                    return "Item successfully deleted";
//                }
//                throw new ResourceNotFoundException("Item does not exist");
//

        }
        throw new UnauthorizedUserException("User does not exist");
    }

    @Override
    public CartDto viewCartByCustomer() {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = cartRepository.findByCustomer(loggedInCustomer);
        if (cart.getItems().isEmpty())
            throw new ResourceNotFoundException("Your Cart is empty!");
        return CartDto.builder()
                .items(cart.getItems())
                .total(cart.getTotal()).build();
    }

    @Override
    public List<CartItemResponseDto> fetchProductsFromCustomerCart() {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> items = cart.getItems();
        List<CartItemResponseDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            CartItemResponseDto itemDto = new CartItemResponseDto();
            BeanUtils.copyProperties(item, itemDto);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

}
