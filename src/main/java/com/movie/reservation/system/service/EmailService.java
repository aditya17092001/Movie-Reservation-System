package com.movie.reservation.system.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.movie.reservation.system.model.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public HashMap<String, String> sendOtp(Email to) {
        HashMap<String, String> mailResponse = new HashMap<>();
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(sender);
            mail.setTo(to.getRecipient());
            mail.setText(to.getMsgBody());
            mail.setSubject(to.getSubject());

            javaMailSender.send(mail);
            
            log.info("Email sent successfully!");
            mailResponse.put("message", "OTP sent successfully");
            return mailResponse;

        } catch (Exception e) {
            log.info("Error in sending an Email!");
            mailResponse.put("message", "Error in sending OTP!");
            return mailResponse;
        }
    }
}
