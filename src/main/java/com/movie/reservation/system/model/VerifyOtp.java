package com.movie.reservation.system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "verify_otp")
public class VerifyOtp {
    
    @Id
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    
    private String otp;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime otp_createdAt = LocalDateTime.now();
}
