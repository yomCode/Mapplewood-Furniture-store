package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.NotAvailableException;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;

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
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final TokenRepository tokenRepository;


    @Override
    public String addItemToCart(Long productId) {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotAvailableException("Product" +
                " not available"));
        Set<Item> allCartItems = cart.getItems();

        if (product.getAvailableQty() == 0) {
            throw new NotAvailableException("Product out of stock");
        } else if(allCartItems.contains(product.getItem())){
            throw new AlreadyExistsException(product.getName() + " already in cart");
        }

        Item newCartItem = Item.builder()
                .imageUrl(product.getImageUrl())
                .productName(product.getName())
                .unitPrice(product.getPrice())
                .orderQty(1)
                .product(product)
                .cart(cart)
                .build();
        newCartItem.setSubTotal(newCartItem.getOrderQty() * product.getPrice());

        itemRepository.save(newCartItem);
        allCartItems.add(newCartItem);

        cart.setItems(allCartItems);
        Double cartTotal = 0.0;

        for (Item item : cart.getItems()) {
            cartTotal += item.getSubTotal();
        }

        cart.setTotal(cartTotal);
        cartRepository.save(cart);
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
    public String addToItemQuantity(Long productId) {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();
        String response = "";

        Item foundItem = itemRepository.findByProductId(productId);

        synchronized (cartItems) {
            for (Item item : cartItems) {
                if (item.getProduct().getId() == foundItem.getProduct().getId()) {
                    if (foundItem.getProduct().getAvailableQty() == 0)
                        throw new NotAvailableException(foundItem.getProduct().getName() + " out of stock");
                    foundItem.setOrderQty(item.getOrderQty() + 1);
                    foundItem.setSubTotal(item.getUnitPrice() * item.getOrderQty());

                    Double cartTotal = cart.getTotal();
                    cartTotal += foundItem.getUnitPrice();
                    cart.setTotal(cartTotal);

                    itemRepository.save(foundItem);
                    cartRepository.save(cart);
                } else {
                    throw new NotAvailableException("Product not in cart");
                }
            }
        }
        return "Item quantity updated successfully";
    }

    @Override
    public String reduceItemQuantity(Long itemId) {
        String response = "";
        Item foundItem = itemRepository.findById(itemId).orElseThrow(() -> new NotAvailableException("Item not available"));
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();
        for(Item item : cartItems) {
            if(item.getProduct().getId() == foundItem.getProduct().getId()){
                item.setOrderQty(item.getOrderQty() - 1);
                item.setSubTotal(item.getUnitPrice() * item.getOrderQty());
                itemRepository.save(item);

                cart.setTotal(cart.getTotal() - item.getUnitPrice());
                cartRepository.save(cart);
                response += item.getProductName() + " quantity updated successfully";
            }

            if(item.getOrderQty() == 0) {
                itemRepository.delete(item);
                response += item.getProductName() + " removed from cart";
            }
        };
        customerRepository.save(loggedInCustomer);
        return response;
    }

    @Override
    public String clearCart(){
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();
        cartItems.clear();
        cart.setItems(cartItems);
        cart.setTotal(0.0);
        cartRepository.save(cart);
        itemRepository.deleteAll();
        return "Cart cleared successfully";
    }

    @Override
    public List<Item> getAllCartItems(){
        Set<Item> cartItems = customerService.getCurrentlyLoggedInUser().getCart().getItems();
        List<Item> cartItemsList = new ArrayList<>();
        cartItemsList.addAll(cartItems);
        return cartItemsList;
    }
}
