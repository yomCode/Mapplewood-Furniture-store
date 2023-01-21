package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> viewOrderHistory(int pageNo, int pageSize);
    OrderResponseDto viewAParticularOrder(Long orderId);

}
