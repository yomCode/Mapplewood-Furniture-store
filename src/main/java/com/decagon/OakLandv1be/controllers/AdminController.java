package com.decagon.OakLandv1be.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/index")
    public String getIndex(){
        return "Hello, I'm now working!";
    }
}
