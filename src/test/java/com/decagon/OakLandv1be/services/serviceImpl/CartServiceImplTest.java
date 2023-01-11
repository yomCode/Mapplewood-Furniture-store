package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CartServiceImplTest {
    @InjectMocks
    private CartServiceImpl cartService;
    @Mock
    private ItemRepository itemRepository;

    @Test

    public void removeItemsInCart() {
        Item item = new Item();
        Product product = Product.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                .color("green").description("strong black").build();
        item.setId(2L);

        item.setProductName(product.getName());
        item.setOrderQty(3);
        item.setUnitPrice(100.00);
        item.setSubTotal(300.00);
       when(itemRepository.save(item)).thenReturn(item);
       when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
       // System.out.println();
        cartService.removeItem(item.getId());
        verify(itemRepository).deleteById(item.getId());
        // verify(itemRepository,times(1)).deleteById(anyLong());
    }

//    @Test(expected = RuntimeException.class)
//    public void should_throw_exception_when_item_doesnt_exist(){
//        Item item = new Item();
//        item.setId(89L);
//        given(itemRepository.findById(anyLong())).will
//    }


}