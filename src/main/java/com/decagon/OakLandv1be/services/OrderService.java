package com.decagon.OakLandv1be.services;


import com.decagon.OakLandv1be.dto.OrderResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> viewOrderHistory(int pageNo, int pageSize);
    OrderResponseDto viewAParticularOrder(Long orderId);
    Page<OrderResponseDto> viewAllOrdersPaginated(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
}
