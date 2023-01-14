package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.NotAvailableException;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.services.CustomerService;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Set;



@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CartRepository cartRepository;


    @Override
    public String addItemToCart(Long productId, AddItemToCartDto addItemToCartDto) {

        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotAvailableException("Product not available"));

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
