package com.movie.reservation.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.reservation.system.constants.RestURLs;
import com.movie.reservation.system.dto.ResetPasswordRequestDTO;
import com.movie.reservation.system.dto.UserSignInDTO;
import com.movie.reservation.system.dto.UserSignUpDTO;
import com.movie.reservation.system.model.Users;
import com.movie.reservation.system.repository.UserRepo;
import com.movie.reservation.system.service.JwtService;
import com.movie.reservation.system.service.UserService;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
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

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtService jwtService;

    @PostMapping(RestURLs.SIGNUP)
    public ResponseEntity<?> userSignup(@RequestBody UserSignUpDTO user) {
        HashMap<String, String> response = new HashMap<>();
    
        if(user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            response.put("message", "Email and Password can't be empty.");
            log.info("Email and Password can't be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(repository.findByEmail(user.getEmail()) != null) {
            log.info("User already exist");
            response.put("message", "User already exist, Please signin.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Users newUser = new Users();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setPhoneNo(user.getPhoneNo());
        newUser.setName(user.getName());
        newUser.setGender(user.getGender());
        repository.save(newUser);

        String jwt = jwtService.generateToken(newUser.getUser_id());

        log.info("User registered successfully");
        response.put("message", "User registered successfully.");
        response.put("token", jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping(RestURLs.SIGNIN)
    public ResponseEntity<?> userSignin(@RequestBody UserSignInDTO user) {
        HashMap<String, String> response = new HashMap<>();
    
        if(user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            log.info("Email and Password can't be empty");
            response.put("message", "Email and Password can't be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            String jwt = userService.verify(user);
            log.info("Signin successful.");
            response.put("message", "Signin successful.");
            response.put("token", jwt);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.info("Invalid Credentials.");
            response.put("message", "Invalid Credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping(RestURLs.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        Users user = repository.findByEmail(resetPasswordRequestDTO.getEmail());

        HashMap<String, String> response = new HashMap<>();
        if(user == null) {
            log.info("User not found with this email.");
            response.put("message", "User not found with this email.");
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
        }

        if(!passwordEncoder.matches(resetPasswordRequestDTO.getCurrentPassword(), user.getPassword())) {
            log.info("Current password is incorrect.");
            response.put("message", "Current password is incorrect.");

            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        String updatedPassword = resetPasswordRequestDTO.getUpdatePassword();
        String encryptedPassword = passwordEncoder.encode(updatedPassword);
        user.setPassword(encryptedPassword);

        repository.save(user);

        log.info("Password reset successfully.");
        response.put("message", "Password reset successfully.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
