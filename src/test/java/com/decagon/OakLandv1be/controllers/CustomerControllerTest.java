package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.OakLandV1BeApplication;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.services.serviceImpl.CartServiceImpl;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;

import org.mockito.Mock;

import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.services.serviceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = OakLandV1BeApplication.class)
=======
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)

class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CartServiceImpl cartService;

    @MockBean
    private ResponseManager responseManager;
    @MockBean
    CustomerService customerService;


    @Test
    void CustomerController_AddItemToCart_ReturnResponseEntity() {
        try{

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

            Item item = new Item();
            item.setId(1L);
            item.setProductName("ArmChair");
            item.setImageUrl("abcd");
            item.setUnitPrice(2000.0);
            item.setOrderQty(20);
            item.setSubTotal(item.getUnitPrice() * item.getOrderQty());

            Set<Item> cartItemsSet = new HashSet<>();
            cartItemsSet.add(item);

            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setItems(cartItemsSet);
            cart.setTotal(40000.0);

            AddItemToCartDto addItemToCartDto = new AddItemToCartDto();
            addItemToCartDto.setOrderQty(20);

            SignupResponseDto signupResponseDto = SignupResponseDto.builder().gender(Gender.FEMALE).phone("1234").email("a@mail.com").date_of_birth("10-06-1992").address("123").firstName("Aishat").lastName("Moshood").verificationStatus(true).build();

            CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
            cartItemResponseDto.setProductName("ArmChair");
            cartItemResponseDto.setImageUrl("abcd");
            cartItemResponseDto.setUnitPrice(2000.0);
            cartItemResponseDto.setOrderQty(20);
            cartItemResponseDto.setSubTotal(cartItemResponseDto.getUnitPrice() * cartItemResponseDto.getOrderQty());
            cartItemResponseDto.setCart(cart);
            cartItemResponseDto.setCustomer(signupResponseDto);

            ApiResponse apiResponse = new ApiResponse<>("Request Successful",true, cartItemResponseDto);

            when(customerService.getCurrentlyLoggedInUser()).thenReturn(customer);
            when(cartService.addItemToCart(anyLong(),addItemToCartDto)).thenReturn(cartItemResponseDto);
            when(responseManager.success(cartItemResponseDto)).thenReturn(apiResponse);

            mockMvc.perform(post("/api/v1/auth/customer/cart/item/add?productId",1L).contentType("application/json"))
                    .andExpect(status().isCreated());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


