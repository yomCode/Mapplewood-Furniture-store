package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.SignupRequestDto;

import com.decagon.OakLandv1be.dto.SignupResponseDto;

import com.decagon.OakLandv1be.dto.*;

import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface CustomerService {


    SignupResponseDto saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException;

    ResponseEntity<ApiResponse> verifyRegistration(String token);

    public void editProfile(EditProfileRequestDto editProfileRequestDto);


}
