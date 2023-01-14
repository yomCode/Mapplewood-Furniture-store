package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.config.userDetails.AppUserDetailsService;
import com.decagon.OakLandv1be.dto.ForgotPasswordRequestDto;
import com.decagon.OakLandv1be.dto.LoginDto;
import com.decagon.OakLandv1be.dto.PasswordResetDto;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.exceptions.InvalidAttributeException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.dto.UpdatePasswordDto;
import com.decagon.OakLandv1be.services.serviceImpl.PersonServiceImpl;

import com.decagon.OakLandv1be.services.PersonService;
import com.decagon.OakLandv1be.services.serviceImpl.PersonServiceImpl;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;

    private final PersonService personService;
    

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@Valid @RequestBody LoginDto loginRequest) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        if(!user.isEnabled())
            throw new UsernameNotFoundException("You have not been verified. Check your email to be verified!");
        if (!user.isAccountNonLocked()){
            return new ApiResponse<>("This account has been deactivated", false, null);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(authentication != null)
            return new ApiResponse<>("Login Successful",
                    true, tokenService.generateToken(authentication), HttpStatus.OK);

        return new ApiResponse<>("Invalid Username or Password",
                false, null, HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/forgot-password-request")
    public ResponseEntity<String> passwordRequestReset(@Valid @RequestBody ForgotPasswordRequestDto requestDto) throws IOException {
        String result = personService.forgotPasswordRequest(requestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> passwordReset(@PathVariable String token, @Valid @RequestBody PasswordResetDto passwordResetDto){
        String result = personService.resetPassword(token, passwordResetDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ApiResponse<String> updatePassword(@Valid  @RequestBody UpdatePasswordDto updatePasswordDto){
        personService.updatePassword( updatePasswordDto);
        return new ApiResponse<>("Password changed successfully",true ,null,HttpStatus.ACCEPTED);
    }

}
