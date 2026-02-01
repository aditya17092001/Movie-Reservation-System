package com.movie.reservation.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.reservation.system.constants.RestURLs;
import com.movie.reservation.system.model.Users;
import com.movie.reservation.system.repository.UserRepo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(RestURLs.V1_API+RestURLs.AUTH)
public class UserAuth {

    @Autowired
    private UserRepo repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping(RestURLs.SIGNUP)
    public ResponseEntity<?> userSignup(@RequestBody Users user) {
        if(user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            log.info("Email and Password can't be empty");
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
                .body("Email and Password can't be empty.");
        }

        Users user_existing = repository.findByEmail(user.getEmail());

        HashMap<String, String> response = new HashMap<>();

        if(user_existing != null) {
            log.info("User already exist");
            response.put("message", "User already exist, Please signin.");

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        String password = user.getPassword();
        String encryptedPassword = passwordEncoder.encode(password);
        log.info("Encrypted Password: "+encryptedPassword);
        user.setPassword(encryptedPassword);
        repository.save(user);

        log.info("User saved successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }


    @PostMapping(RestURLs.SIGNIN)
    public ResponseEntity<?> userSignin(@RequestBody Users user) {
        if(user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body("Email and Password can't be empty.");

        Users existing_user = repository.findByEmail(user.getEmail());
        
        if(existing_user != null && passwordEncoder.matches(user.getPassword(), existing_user.getPassword())) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(existing_user);
        } else {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid Credentials.");
        }
    }
    
    
}
