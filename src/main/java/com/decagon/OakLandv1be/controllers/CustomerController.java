package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.utils.ApiResponse;
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
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
        customerService.saveCustomer(signupRequestDto);
        return new ResponseEntity<>("Registration Successful! Check your mail to verify your account.", HttpStatus.CREATED);
    }
    @GetMapping("/verifyRegistration/{token}")
    public ResponseEntity<ApiResponse> verifyAccount(@PathVariable String token){
        return customerService.verifyRegistration(token);
    }
}
