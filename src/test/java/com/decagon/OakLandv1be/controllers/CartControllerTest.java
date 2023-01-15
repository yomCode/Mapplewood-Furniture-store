//package com.decagon.OakLandv1be.controllers;
//
//
//import com.decagon.OakLandv1be.entities.Cart;
//import com.decagon.OakLandv1be.entities.Customer;
//import com.decagon.OakLandv1be.entities.Item;
//import com.decagon.OakLandv1be.entities.Person;
//import com.decagon.OakLandv1be.services.serviceImpl.CartServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@WebMvcTest
//@ContextConfiguration(classes = CartController.class)
//class CartControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private CartServiceImpl cartService;
//
//    private Customer customer;
//    private Person person;
//    private Item item;
//
//    @Test
//    public void removeItemFromCart() throws Exception {
//        Set<Item> itemsSet = new HashSet<>();
//        Cart cart = new Cart();
//        cart.setId(1L);
//        cart.setItems(itemsSet);
//        customer = Customer.builder()
//                .person(person)
//                .cart(cart)
//                .wallet(null)
//                .build();
//        item = Item.builder()
//                .productName("cushion wood")
//                .cart(null)
//                .order(null)
//                .imageUrl("img/url")
//                .orderQty(3)
//                .unitPrice(200.00)
//                .subTotal(600.00)
//                .build();
//        item.setId(1L);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .delete("/api/v1/cart/item/delete/{itemId}",1L)
//                .contentType(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//}