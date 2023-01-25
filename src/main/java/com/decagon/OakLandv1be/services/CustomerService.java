package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;

import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    SignupResponseDto saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException;

    ResponseEntity<ApiResponse> verifyRegistration(String token);

    void editProfile(EditProfileRequestDto editProfileRequestDto);

    void addProductToFavorites(Long pid);

    void removeProductFromFavorites(Long pid);

    Customer getCurrentlyLoggedInUser();

    CustomerProfileDto viewProfile();

    Page<CustomerProfileDto> viewAllCustomersProfileWithPaginationSorting(Integer pageNumber, Integer pageSize, String sortBy);
}
