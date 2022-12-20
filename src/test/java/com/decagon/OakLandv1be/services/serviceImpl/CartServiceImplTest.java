package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class CartServiceImplTest {

    @InjectMocks
    CartServiceImpl cartServiceImpl;
    @Mock
    ProductRepository productRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    PersonRepository personRepository;
    @MockBean
    CustomerService customerService;

    @Test
    @WithMockUser("a@mail.com")
    void testCurrentUserEmail() {
        String message = cartServiceImpl.currentUserEmail();
    }

    @Test
    //@WithMockUser(customer)
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

        Product product = new Product();
        product.setId(1L);
        product.setName("ArmChair");
        product.setImageUrl("abcd");
        product.setAvailableQty(50);
        product.setPrice(2000.0);

        Item item = new Item();
        item.setId(1L);
        item.setProductName("ArmChair");
        item.setImageUrl("abcd");
        item.setUnitPrice(2000.0);
        item.setOrderQty(20);
        item.setSubTotal(item.getUnitPrice() * item.getOrderQty());
        item.setProduct(product);

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

        Set<Item> allCartItems = new HashSet<>();
        allCartItems.add(item);

        List<Item> cartItemsList = new ArrayList<>();
        cartItemsList.addAll(allCartItems);


        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(customerService.getCurrentlyLoggedInUser()).thenReturn(any());
        //when(cartServiceImpl.getNewCartItem(addItemToCartDto.getOrderQty(),product,customer,cartItemsList)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);
        assertEquals(cartItemResponseDto,cartServiceImpl.addItemToCart(1L,addItemToCartDto));
    }
}