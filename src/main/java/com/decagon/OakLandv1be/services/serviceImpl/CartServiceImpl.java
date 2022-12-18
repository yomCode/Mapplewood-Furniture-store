package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    @Override
    public Cart removeItemInCart() {
        Product product = new Product();
        Customer customer = new Customer();

        Cart cart = customer.getCart();
        Set<Item> items = cart.getItems();

        Item item = findItems(items, product.getId());
        items.remove(item);
        itemRepository.delete(item);
        double totalPrice = totalPrice(items);
        //  int totalItems = totalItems(items);

        cart.setItems(items);
        cart.setTotal(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public void removeItem(Long itemId) {

    }

    private Item findItems(Set<Item> items, Long productId) {
        if (items == null) {
            return null;
        }
        Item cartItem = null;

        for (Item item : items) {
            if (item.getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<Item> items) {
        int totalItems = 0;
        for (Item item : items) {
            totalItems += item.getOrderQty();
        }
        return totalItems;
    }

    private double totalPrice(Set<Item> items) {
        double totalPrice = 0.0;

        for (Item item : items) {
            totalPrice += item.getSubTotal();
        }
        return totalPrice;
    }
}
