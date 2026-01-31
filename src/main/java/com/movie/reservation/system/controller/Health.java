package com.movie.reservation.system.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class Health {

    @GetMapping("/healthz")
    public String getMethodName() {
        log.info("Server is healthy!");
        return new String("Healthy");
    }
    
}
