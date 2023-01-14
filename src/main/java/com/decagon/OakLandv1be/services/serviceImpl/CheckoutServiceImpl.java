package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutDto;
import com.decagon.OakLandv1be.dto.CheckoutResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import com.decagon.OakLandv1be.exceptions.InvalidPaymentMethodException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.repositries.AddressRepository;
import com.decagon.OakLandv1be.repositries.ModeOfPaymentRepository;
import com.decagon.OakLandv1be.repositries.OrderRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final PersonRepository personRepository;
    private final ModeOfPaymentRepository modeOfPaymentRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    @Override
    public ResponseEntity<CheckoutResponseDto> cartCheckout(CheckoutDto checkoutDto){
        // Get the currently logged in user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedUserException("Login to checkout");
        }
        String email = auth.getName();
        // Get the customer and their cart
        Customer customer = personRepository.findByEmail(email)
                .map(Person::getCustomer)
                .orElseThrow(() -> new UnauthorizedUserException("Invalid user"));
        Cart cart = customer.getCart();
        // Get the selected address
        Address address = addressRepository.findById(checkoutDto.getAddress_id())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        // Create a new Order and Delivery
        Order order = new Order();
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.TO_ARRIVE);
        order.setItems(cart.getItems());
        // Calculate the total and set order details
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getOrderQty())
                .sum();
        order.setAddress(address);
        order.setCustomer(customer);
        order.setDiscount(0.00);
        order.setDeliveryFee(0.00);
        order.setModeOfDelivery(this.modeOfDelivery(checkoutDto.getModeOfDelivery()));
        order.setModeOfPayment(modeOfPayment(checkoutDto.getModeOfPayment()));
        order.setDelivery(delivery);
        order.setGrandTotal((total - order.getDiscount()) + order.getDeliveryFee());
        // Save the order and create the response
        orderRepository.save(order);
        CheckoutResponseDto response = CheckoutResponseDto.builder()
                .customer(order.getCustomer())
                .items(order.getItems())
                .deliveryFee(order.getDeliveryFee())
                .discount(order.getDiscount())
                .grandTotal(order.getGrandTotal())
                .delivery(order.getDelivery())
                .modeOfPayment(order.getModeOfPayment())
                .modeOfDelivery(order.getModeOfDelivery())
                .address(order.getAddress())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @Override
    public ModeOfPayment modeOfPayment(String modeOfPayment){
        return modeOfPaymentRepository.findByName(modeOfPayment).orElseThrow(
                () -> new InvalidPaymentMethodException("This payment method is invalid"));
    }
    @Override
    public ModeOfDelivery modeOfDelivery(String deliveryMethod){
        return ModeOfDelivery.valueOf(deliveryMethod.toUpperCase());
    }
    @Override
    public ResponseEntity<String> addNewAddress(AddressRequestDto addressRequestDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        if(!(auth instanceof AnonymousAuthenticationToken)){
            Person person = personRepository.findByEmail(email)
                    .orElseThrow(()->new UnauthorizedUserException("Login to add checkout address"));

            Customer customer = person.getCustomer();

            Address address = Address.builder()
                    .emailAddress(addressRequestDto.getEmailAddress())
                    .state(addressRequestDto.getState())
                    .country(addressRequestDto.getCountry())
                    .street(addressRequestDto.getStreet())
                    .fullName(addressRequestDto.getFullName())
                    .customer(customer)
                    .build();
             addressRepository.save(address);

             return new ResponseEntity<>("Address saved successfully", HttpStatus.OK);
        }
        throw new UnauthorizedUserException("Please login to add a new address");
    }

}
