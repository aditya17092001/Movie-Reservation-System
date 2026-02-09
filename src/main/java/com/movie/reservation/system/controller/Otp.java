package com.movie.reservation.system.controller;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.reservation.system.constants.RestURLs;
import com.movie.reservation.system.dto.EmailVerifyDTO;
import com.movie.reservation.system.model.Email;
import com.movie.reservation.system.model.Users;
import com.movie.reservation.system.model.VerifyOtp;
import com.movie.reservation.system.repository.UserRepo;
import com.movie.reservation.system.repository.VerifyOtpRepo;
import com.movie.reservation.system.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(RestURLs.V1_API+RestURLs.AUTH)
public class Otp {

    @Autowired
    private EmailService emailService;

    @Autowired 
    private VerifyOtpRepo verifyOtpRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @PostMapping(RestURLs.VERIFY_OTP)
    public ResponseEntity<?> verifyOTP(@RequestBody EmailVerifyDTO email) {
        log.info("Verify Email request initiated");
        VerifyOtp existingEntry = verifyOtpRepo.findByEmail(email.getEmail()).orElse(null);

        HashMap<String, String> response = new HashMap<>();
        if(existingEntry == null) {
            log.debug("User doesnt exist please signup");
            response.put("message", "Something went wrong, Please try again!");
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        boolean isExpired = LocalDateTime.now().isAfter(existingEntry.getOtp_createdAt().plusMinutes(10));

        if(isExpired) {
            log.info("OTP expired!");
            response.put("message", "OTP is expired, Please try again!");
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        if(email.getOtp().equals(existingEntry.getOtp())) {
            log.info("Otp Succesfull");

            Users user = userRepo.findByEmail(existingEntry.getEmail());
            user.setEmailVerified(true);
            userRepo.save(user);

            verifyOtpRepo.delete(existingEntry);
            log.debug("OTP entry deleted after successful verification");
            
            response.put("message", "Otp verfication succesfull!");
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
        }

        log.info("Invalid Otp");
        response.put("message", "Invalid Otp!");
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
    } 

    public HashMap<String, String> sendOTP(Email email) {   
        saveOtpEntry(email);
        
        log.info("Email sending initiated!");
        HashMap<String, String> response = emailService.sendOtp(email);

        return response;
    }

    @Transactional
    private void saveOtpEntry(Email email) {
        VerifyOtp existingEntry = verifyOtpRepo.findByEmail(email.getRecipient()).orElse(null);
        
        if(existingEntry == null) {
            log.debug("User doesn't exist");
            VerifyOtp newEntry = new VerifyOtp();
            newEntry.setEmail(email.getRecipient());
            newEntry.setOtp_createdAt(LocalDateTime.now());
            newEntry.setCreatedAt(LocalDateTime.now());
            newEntry.setOtp(email.getOtp());
            verifyOtpRepo.save(newEntry);
        } else {
            log.debug("User already exist updating otp and otp created time");
            existingEntry.setOtp(email.getOtp());
            existingEntry.setOtp_createdAt(LocalDateTime.now());
            verifyOtpRepo.save(existingEntry);
        }
    }
}
