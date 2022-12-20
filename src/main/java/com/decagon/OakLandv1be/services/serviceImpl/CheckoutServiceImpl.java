package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutItemDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import com.decagon.OakLandv1be.exceptions.InvalidPaymentMethodException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.repositries.ModeOfPaymentRepository;
import com.decagon.OakLandv1be.repositries.OrderRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.CheckoutService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public Order cartCheckout(AddressRequestDto addressRequestDto, CheckoutItemDto checkoutItemDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        if((auth instanceof AnonymousAuthenticationToken))
            throw new UnauthorizedUserException("Login to checkout");

        Person person = personRepository.findByEmail(email).orElseThrow();
        Customer customer = person.getCustomer();
        Cart cart = customer.getCart();

        Order order = new Order();

        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.TO_ARRIVE);

        for(Item item : cart.getItems()){
            order.getItems().add(item);
        }

        Double total = 0.00;

        for(Item item : order.getItems()){
            total += (item.getUnitPrice() * item.getOrderQty());
        }

        order.setAddress(this.addNewAddress(addressRequestDto));
        order.setCustomer(customer);
        order.setDiscount(0.00);
        order.setDeliveryFee(0.00);
        order.setModeOfDelivery(this.modeOfDelivery(checkoutItemDto.getModeOfDelivery()));
        order.setModeOfPayment(modeOfPayment(checkoutItemDto.getModeOfPayment()));
        order.setDelivery(delivery);
        order.setGrandTotal((total - order.getDiscount()) + order.getDeliveryFee());
        orderRepository.save(order);

        return order;
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
    public Address addNewAddress(AddressRequestDto request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        if(!(auth instanceof AnonymousAuthenticationToken)){
            Person person = personRepository.findByEmail(email).orElseThrow();

            Customer customer = person.getCustomer();

            Address address = Address.builder()
                    .emailAddress(request.getEmailAddress())
                    .state(request.getState())
                    .country(request.getCountry())
                    .street(request.getStreet())
                    .fullName(request.getFullName())
                    .customer(customer)
                    .build();
            return address;
        }
        throw new UnauthorizedUserException("Please login to add a new address");
    }

}
