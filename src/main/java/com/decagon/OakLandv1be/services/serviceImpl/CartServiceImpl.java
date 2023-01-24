package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
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
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;


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
//        for(Item item : allCartItems){
//            if(item.getProduct().getId() == productId){
//                throw new AlreadyExistsException(product.getName() + " already in cart");
//            }
//            break;
//        }
        Item newCartItem = new Item();
        BeanUtils.copyProperties(product, newCartItem);
        newCartItem.setProductName(product.getName());
        newCartItem.setUnitPrice(product.getPrice());
        newCartItem.setOrderQty(1);
        newCartItem.setSubTotal(newCartItem.getOrderQty() * product.getPrice());
        itemRepository.save(newCartItem);
        allCartItems.add(newCartItem);

        cart.setItems(allCartItems);
        cartRepository.save(cart);

        Double cartTotal = cart.getTotal();

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

    @Override
    public String addToItemQuantity(Long itemId) {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();

        for(Item item : cartItems) {
            if(item.getId() == itemId){
                Product product = productRepository.findByItem(item);
                if(product.getAvailableQty() == 0) throw new NotAvailableException(product.getName() + " out of stock");
                item.setOrderQty(item.getOrderQty() + 1);
                item.setSubTotal(item.getUnitPrice() * item.getOrderQty());
            }
        }
        customerRepository.save(loggedInCustomer);
        return "Item quantity updated successfully";
    }

    @Override
    public String reduceItemQuantity(Long itemId) {
        String response = "";

        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();
        for(Item item : cartItems) {
            if(item.getId() == itemId){
                if(item.getOrderQty() == 1) {
                    removeItem(itemId);
                    response += item.getProductName() + " removed from cart";
                }

                item.setOrderQty(item.getOrderQty() - 1);
                item.setSubTotal(item.getUnitPrice() * item.getOrderQty());
                response += item.getProductName() + " quantity updated successfully";
            }
        }
        customerRepository.save(loggedInCustomer);
        return response;
    }
}
