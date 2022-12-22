package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @InjectMocks
    CartServiceImpl cartServiceImpl;

    @Mock
    ProductRepository productRepository;

    @Mock
    CustomerService customerService;

    @Mock
    CartRepository cartRepository;;


    @Test
    void testAddItemToCart() throws AlreadyExistsException {
        Person person = new Person();
        person.setGender(Gender.FEMALE);
        person.setPhone("1234");
        person.setEmail("a@mail.com");
        person.setDate_of_birth("10-06-1992");
        person.setAddress("123");
        person.setFirstName("Aishat");
        person.setLastName("Moshood");
        person.setVerificationStatus(true);
        person.setRole(Role.CUSTOMER);

        Customer customer = new Customer();
        customer.setPerson(person);

        Product product = Product.builder()
                .name("Oppola")
                .price(40000.00)
                .availableQty(400)
                .imageUrl("www.google.com")
                .color("yellow")
                .description("lovely fur")
                .build();
        product.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setProductName("Oppola");
        item.setImageUrl("www.google.com");
        item.setUnitPrice(40000.00);
        item.setOrderQty(20);
        item.setSubTotal(item.getUnitPrice() * item.getOrderQty());

        Cart cart = new Cart();

        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
        }

        Set<Item> cartItemsSet = new HashSet<>();
        cartItemsSet.add(item);
        cart.setItems(cartItemsSet);
        cart.setTotal(40000.0);

        AddItemToCartDto addItemToCartDto = new AddItemToCartDto();
        addItemToCartDto.setOrderQty(20);

        CartResponseDto cartResponseDto = new CartResponseDto();
        BeanUtils.copyProperties(item,cartResponseDto);

        when(customerService.getCurrentlyLoggedInUser()).thenReturn(customer);
        when(cartRepository.findByCustomer(customer)).thenReturn(cart);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertEquals(cartResponseDto,cartServiceImpl.addItemToCart(1L,addItemToCartDto));

    }
}