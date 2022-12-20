package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutItemDto;
import com.decagon.OakLandv1be.entities.Address;
import com.decagon.OakLandv1be.entities.ModeOfPayment;
import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;

public interface CheckoutService {
    Order cartCheckout(AddressRequestDto addressRequestDto, CheckoutItemDto checkoutItemDto);
    ModeOfPayment modeOfPayment(String modeOfPayment);

    ModeOfDelivery modeOfDelivery(String deliveryMethod);

    Address addNewAddress(AddressRequestDto request);
}
