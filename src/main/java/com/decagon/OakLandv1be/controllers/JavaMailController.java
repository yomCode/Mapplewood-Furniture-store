package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.services.JavaMailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class JavaMailController {
    private final JavaMailService javaMailService;
    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendMail(@PathVariable String email) throws IOException {
        return javaMailService.sendMail(email);
    }

}
