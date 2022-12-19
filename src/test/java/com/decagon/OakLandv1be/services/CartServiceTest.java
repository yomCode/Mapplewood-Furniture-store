package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.services.serviceImpl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
@InjectMocks
    private CartServiceImpl cartService;
@Mock
    ItemRepository itemRepository;
@Test
    public void removeItemsInCart() {
    Item item = new Item();
    item.setId(2L);
    cartService.removeItem(item.getId());
    verify(itemRepository,times(1)).deleteById(anyLong());
}

}