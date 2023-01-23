package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.OrderRepository;
import com.decagon.OakLandv1be.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static com.decagon.OakLandv1be.enums.ModeOfDelivery.DOORSTEP;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
    @Mock
    CustomerRepository customerRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    private Order order;
    private OrderResponseDto orderResponseDto;
    private ModeOfPayment modeOfPayment;
    private Delivery delivery;
    private Address address;
    private Transaction transaction;
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        modeOfPayment = new ModeOfPayment();
        delivery = new Delivery();
        address = Address.builder().fullName("Decagon").emailAddress("mag@gmail.com").street("Ohen Street")
                .country("Edo").state("Nigeria").customer(customer).build();
        transaction = new Transaction();
         order = Order.builder()
                .items(new HashSet<>()).modeOfPayment(modeOfPayment).modeOfDelivery(DOORSTEP).deliveryFee(2000.00)
                .delivery(delivery).grandTotal(5000.00).discount(500.00).address(address).transaction(transaction).build();
        order.setId(2L);
        orderResponseDto = OrderResponseDto.builder()
                .items(new HashSet<>()).modeOfPayment(modeOfPayment).modeOfDelivery(DOORSTEP).deliveryFee(2000.00)
                .delivery(delivery).grandTotal(5000.00).discount(500.00).address(address).transaction(transaction).build();
    }

    @Test
    void viewAParticularOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        OrderResponseDto actual = orderService.viewAParticularOrder(2L);
        assertEquals(0, orderResponseDto.getItems().size());
        assertNotNull(orderResponseDto.getAddress());
        assertNotNull(orderResponseDto.getTransaction());
        assertEquals(orderResponseDto, actual);
    }


}