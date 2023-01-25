package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.OrderRepository;
import com.decagon.OakLandv1be.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;

    @Override
    public List<OrderResponseDto> viewOrderHistory(int pageNo, int pageSize) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Customer customer = customerRepository.findByPersonEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Order> orderList = customer.getOrders();
        if (orderList.isEmpty())
            throw new EmptyListException("You have no order history");

        List<Order> sortedOrders = orderList.stream().sorted(Comparator.comparing(Order::getCreatedAt)
                .reversed()).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedOrders.size());
        List<Order> orders = new ArrayList<>(sortedOrders).subList(start, end);

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order: orders){
            mapToResponse(order,orderResponseDtos);
        }
        return orderResponseDtos;
    }

    private static void mapToResponse(Order order, List<OrderResponseDto> orderResponseDtos) {
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


    @Override
    public OrderResponseDto viewAParticularOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return OrderResponseDto.builder()
                .modeOfPayment(order.getModeOfPayment())
                .items(order.getItems())
                .deliveryFee(order.getDeliveryFee())
                .modeOfDelivery(order.getModeOfDelivery())
                .delivery(order.getDelivery())
                .grandTotal(order.getGrandTotal())
                .discount(order.getDiscount())
                .address(order.getAddress())
                .transaction(order.getTransaction())
                .build();
    }
}
