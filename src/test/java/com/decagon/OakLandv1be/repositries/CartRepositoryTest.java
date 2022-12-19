package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertNull;


class CartRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void removeItem(){
        Item item = new Item();
        item.setId(2L);
    itemRepository.deleteById(item.getId());
    assertNull(itemRepository.findById(item.getId()));
}
}