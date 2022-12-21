package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.services.serviceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void editProfile() throws Exception {
        EditProfileRequestDto editProfileRequestDto = new EditProfileRequestDto();
        editProfileRequestDto.setFirstName("Many");
        editProfileRequestDto.setLastName("Rob");
        editProfileRequestDto.setGender(Gender.MALE);
        editProfileRequestDto.setDate_of_birth("11-01-1993");
        editProfileRequestDto.setPhone("07068693321");

        String requestBody = mapper.writeValueAsString(editProfileRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/customer/edit-profile")
                        .contentType("application/json").content(requestBody))
                .andExpect(status().isOk());
    }
}
