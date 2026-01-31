package com.movie.reservation.system.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Welcome {
    @GetMapping("/welcome")
    public String getMethodName() {
        return new String("Welcome");
    }
    
}
