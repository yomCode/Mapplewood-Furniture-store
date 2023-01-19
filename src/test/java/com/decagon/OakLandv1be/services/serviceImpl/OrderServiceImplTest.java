package com.decagon.OakLandv1be.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Set;
import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @Mock
    Order order;
    @Mock
    Customer customer;
    Set<Order> orders;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orders = Set.of(order);
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void viewOrderHistory_authenticated_returnsOrderHistory() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(customerRepository.findByPersonEmail("customer@email.com"))
                .thenReturn(java.util.Optional.of(customer));
        when(customer.getOrders())
                .thenReturn(orders);
        when(authentication.getName()).thenReturn("customer@email.com");


        List<OrderResponseDto> orderHistory = orderService.viewOrderHistory();

        assertEquals(1, orderHistory.size());
    }

    @Test
    void viewOrderHistory_notAuthenticated_throwsResourceNotFoundException() {

        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        assertThrows(ResourceNotFoundException.class, () -> orderService.viewOrderHistory());
    }

}
