package com.decagon.OakLandv1be.controllers;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)

class CartControllerTest {


    @Mock
    private CartService cartService;

    @Mock
    private ResponseManager responseManager;

    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    public void testDeleteItem() throws Exception {
        // Set up mock service response
        when(responseManager.success("Item removed successfully")).thenReturn(new ApiResponse<>("success", "Item removed successfully",HttpStatus.OK));

        // Perform DELETE request to delete item
        mockMvc.perform(delete("/api/v1/cart/item/delete/1"))
                .andExpect(status().isOk());

        // Verify that cartService.removeItem was called with the correct itemId
        verify(cartService).removeItem(1L);

        // Verify that responseManager.success was called with the correct message
        verify(responseManager).success("Item removed successfully");
    }

//    @Mock
//    private CartService cartService;
//
//    @InjectMocks
//    private CartController cartController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
//    }
//
//    @Test
//    public void deleteItem_whenItemIdExist_returnsOk() throws Exception {
//        // arrange
//        Long itemId = 1L;
//        when(cartService.removeItem(eq(itemId))).thenReturn("Item deleted");
//        // act
//        mockMvc.perform(delete("/api/v1/cart/item/delete/{itemId}", itemId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        // assert
//        verify(cartService).removeItem(eq(itemId));
//    }


}


