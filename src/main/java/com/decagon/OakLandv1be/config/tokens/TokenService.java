package com.decagon.OakLandv1be.config.tokens;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String generateToken(UserDetails user);
    String generatePasswordResetToken(String email);
    String generateVerificationToken(String email);
}
