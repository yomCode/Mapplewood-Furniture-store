package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import com.decagon.OakLandv1be.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static com.decagon.OakLandv1be.enums.ModeOfDelivery.DOORSTEP;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderController orderController;
    @MockBean
    private OrderService orderService;

    @Test
    void viewASingleOrder() {
        try {
            Customer customer = new Customer();
            customer.setId(1L);
            ModeOfPayment modeOfPayment = new ModeOfPayment();
            Delivery delivery = new Delivery();
            Address address = Address.builder().fullName("Decagon").emailAddress("mag@gmail.com").street("Ohen Street")
                    .country("Edo").state("Nigeria").customer(customer).build();
            Transaction transaction = new Transaction();
            Order order = Order.builder()
                    .items(new HashSet<>()).modeOfPayment(modeOfPayment).modeOfDelivery(DOORSTEP).deliveryFee(2000.00)
                    .delivery(delivery).grandTotal(5000.00).discount(500.00).address(address).transaction(transaction).build();
            order.setId(2L);
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .items(new HashSet<>()).modeOfPayment(modeOfPayment).modeOfDelivery(DOORSTEP).deliveryFee(2000.00)
                    .delivery(delivery).grandTotal(5000.00).discount(500.00).address(address).transaction(transaction).build();
            when(orderService.viewAParticularOrder(anyLong())).thenReturn(orderResponseDto);
            mockMvc.perform(get("/api/v1/customer/order/{orderId}", 12L)
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }
}