package com.decagon.OakLandv1be.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//This is for testing purposes not the main code. Not to be integrated.
@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/home")
    public String getHomepage() {
        return "This is the homepage. Free to everyone!";
    }

    @GetMapping("/")
    public String home(Principal principal) {
        return principal == null ? "Hello, Guest" : "Welcome, " + principal.getName();
    }

    @GetMapping("/api/v1/super-admin")
    public String getSuperAdminPage() {
        return "This is the Super admin page!";
    }

    @GetMapping("/api/v1/admin")
    public String getAdminPage() {
        return "This is the admin page!";
    }

    @GetMapping("/api/v1/customer")
    public String getClientPage() {
        return "This is the client page!";
    }

}
