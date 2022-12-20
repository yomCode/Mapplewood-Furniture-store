package com.decagon.OakLandv1be.services.serviceImpl;


import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;


@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {
    private final ItemRepository itemRepository;
    private final PersonRepository personRepository;
    private final CartRepository cartRepository;

    @Override
    public String removeItem(Long itemToRemoveId) {
        //search for the logged in user
        //get his cart
        //cart
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Cart cart = person.getCustomer().getCart();
            if( cart == null) throw new ResourceNotFoundException("cart is empty");
            Set<Item> itemsInCart = cart.getItems();
            System.out.println(itemsInCart.toString());
            for (Item item : itemsInCart) {
                if (item.getId() == itemToRemoveId) {
                    itemsInCart.remove(item);


                    cart.setItems(itemsInCart);
                    cartRepository.save(cart);
                    personRepository.save(person);
                    return "Item successfully deleted";
                }
                throw new ResourceNotFoundException("Item does not exist");
            }
        }
        throw new UnauthorizedUserException("Login to carry out this operation");
    }
}
