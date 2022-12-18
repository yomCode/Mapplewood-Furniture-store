package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Data
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseManager responseManager;
    private final PersonRepository personRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    @Override
    public ApiResponse<SignupResponseDto> saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException {
        boolean emailExist = personRepository.existsByEmail(signupRequestDto.getEmail());

        if(emailExist)
            throw new AlreadyExistsException("This Email address already exists");

        Customer customer = new Customer();

        Address address = Address.builder()
                .fullName(signupRequestDto.getFirstName() + " "+ signupRequestDto.getLastName())
                .emailAddress(signupRequestDto.getEmail())
                .state(signupRequestDto.getState())
                .country(signupRequestDto.getCountry())
                .street(signupRequestDto.getStreet())
                .build();

        Person person = Person.builder()
                .role(Role.CUSTOMER)
                .verificationStatus(false)
                .address(String.valueOf(address))
                .customer(customer)
                .email(signupRequestDto.getEmail())
                .firstName(signupRequestDto.getFirstName())
                .lastName(signupRequestDto.getLastName())
                .phone(signupRequestDto.getPhoneNumber())
                .gender(signupRequestDto.getGender())
                .password(signupRequestDto.getPassword())
                .date_of_birth(signupRequestDto.getDate_of_birth())
                .build();
        customer.setPerson(person);

        Cart cart = Cart.builder()
                .customer(customer)
                .total(0.00)
                .items(new HashSet<>())
                .build();
        customer.setCart(cart);

        customerRepository.save(customer);
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        BeanUtils.copyProperties(customer, signupResponseDto);

        return responseManager.success(signupResponseDto);
    }

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
