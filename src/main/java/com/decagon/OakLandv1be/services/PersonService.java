package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.ForgotPasswordRequestDto;
import com.decagon.OakLandv1be.dto.PasswordResetDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PersonService {

    String forgotPasswordRequest(ForgotPasswordRequestDto requestDto) throws IOException;
    String resetPassword(String token, PasswordResetDto passwordResetDto);


}
