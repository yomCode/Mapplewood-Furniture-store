package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/signup")

    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
        SignupResponseDto signupResponseDto = customerService.saveCustomer(signupRequestDto);
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }
    @PostMapping("/verifyRegistration/{token}")
    public ResponseEntity<ApiResponse> verifyAccount(@PathVariable String token){
        return customerService.verifyRegistration(token);
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@Valid @RequestBody EditProfileRequestDto editProfileRequestDto){
        customerService.editProfile(editProfileRequestDto);
        return new ResponseEntity<>("Profile Updated Successfully", HttpStatus.OK);
    }
}
