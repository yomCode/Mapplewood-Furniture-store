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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        if((auth instanceof AnonymousAuthenticationToken))
            throw new UnauthorizedUserException("Login to checkout");
        Person person = personRepository.findByEmail(email).orElseThrow();
        Customer customer = person.getCustomer();
        Cart cart = customer.getCart();
        Address address = addressRepository.findById(checkoutDto.getAddress_id()).orElseThrow(()->
                new ResourceNotFoundException("Address not found"));

        Order order = new Order();

        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.TO_ARRIVE);

        for(Item item : cart.getItems()){
            order.getItems().add(item);
        }

        double total = 0.00;
        for(Item item : order.getItems()){
            total += (item.getUnitPrice() * item.getOrderQty());
        }
        order.setAddress(address);
        order.setCustomer(customer);
        order.setDiscount(0.00);
        order.setDeliveryFee(0.00);
        order.setModeOfDelivery(this.modeOfDelivery(checkoutDto.getModeOfDelivery()));
        order.setModeOfPayment(modeOfPayment(checkoutDto.getModeOfPayment()));
        order.setDelivery(delivery);
        order.setGrandTotal((total - order.getDiscount()) + order.getDeliveryFee());
        orderRepository.save(order);

        CheckoutResponseDto checkoutResponseDto = CheckoutResponseDto.builder()
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

        return new ResponseEntity<>(checkoutResponseDto, HttpStatus.OK);
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
            Person person = personRepository.findByEmail(email).orElseThrow();

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

             return new ResponseEntity<>("Address saved", HttpStatus.OK);
        }
        throw new UnauthorizedUserException("Please login to add a new address");
    }

}
