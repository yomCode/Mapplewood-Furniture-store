package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.config.userDetails.AppUserDetailsService;
import com.decagon.OakLandv1be.dto.LoginDto;
import com.decagon.OakLandv1be.services.serviceImpl.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final PersonServiceImpl personService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;

    public ResponseEntity<String> loginPerson(@RequestBody LoginDto loginDto) {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginDto loginRequest) {
        System.out.println("LOGIN REQUEST: " + loginRequest);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        System.out.println("LOGIN REQUEST 2: " + loginRequest);
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        System.out.println("LOGIN USER 2: " + user);

        if (user != null)
            return ResponseEntity.ok(tokenService.generateToken(user));
        else
            return ResponseEntity.status(400).body("Some error has occurred");
    }
}
