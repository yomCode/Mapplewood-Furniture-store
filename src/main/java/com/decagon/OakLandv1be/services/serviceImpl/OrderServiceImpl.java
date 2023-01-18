package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CustomerRepository customerRepository;

    @Override
    public List<OrderResponseDto> viewOrderHistory() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Customer customer = customerRepository.findByPersonEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<Order> orders = customer.getOrders();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order: orders){
            mapToResponse(orderResponseDtos, order);
        }
        return orderResponseDtos;
    }

    private static void mapToResponse(List<OrderResponseDto> orderResponseDtos, Order order) {
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .items(order.getItems())
                .address(order.getAddress())
                .delivery(order.getDelivery())
                .modeOfDelivery(order.getModeOfDelivery())
                .modeOfPayment(order.getModeOfPayment())
                .transaction(order.getTransaction())
                .discount(order.getDiscount())
                .deliveryFee(order.getDeliveryFee())
                .grandTotal(order.getGrandTotal())
                .build();
        orderResponseDtos.add(orderResponseDto);
    }
}
