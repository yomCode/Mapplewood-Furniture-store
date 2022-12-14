package com.decagon.OakLandv1be.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface JavaMailService {
    ResponseEntity<String>sendMail(String receiverEmail) throws IOException;

}
