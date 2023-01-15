package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.OakLandV1BeApplication;
import com.decagon.OakLandv1be.controllers.CustomerController;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.services.serviceImpl.CartServiceImpl;
import com.decagon.OakLandv1be.services.serviceImpl.CustomerServiceImpl;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.mockito.Mock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import static org.mockito.ArgumentMatchers.anyLong;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = OakLandV1BeApplication.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private CartServiceImpl cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ResponseManager responseManager;


//    @Test
//    public void editProfile() throws Exception {
//        EditProfileRequestDto editProfileRequestDto = new EditProfileRequestDto();
//        editProfileRequestDto.setFirstName("Many");
//        editProfileRequestDto.setLastName("Rob");

//        editProfileRequestDto.setGender(Gender.MALE);

//        editProfileRequestDto.setGender(String.valueOf(Gender.MALE));

//        editProfileRequestDto.setDate_of_birth("11-01-1993");
//        editProfileRequestDto.setPhone("07068693321");
//
//        String requestBody = mapper.writeValueAsString(editProfileRequestDto);
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/customer/edit-profile")
//                        .contentType("application/json").content(requestBody))
//                .andExpect(status().isOk());
//    }

//}

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

            Cart cart = person.getCustomer().getCart();

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

            String response = "Item Saved to Cart Successfully";

            ApiResponse apiResponse = new ApiResponse<>("Request Successful",true, response);

            when(customerService.getCurrentlyLoggedInUser()).thenReturn(customer);
            when(cartRepository.findByCustomer(customer)).thenReturn(cart);
            when(cartService.addItemToCart(1L,addItemToCartDto)).thenReturn(response);
            when(responseManager.success(response)).thenReturn(apiResponse);

            mockMvc.perform(post("/api/v1/auth/customer/cart/item/add/productId",1L).contentType("application/json"))
                    .andExpect(status().isCreated());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


