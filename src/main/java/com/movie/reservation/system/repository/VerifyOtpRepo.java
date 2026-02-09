package com.movie.reservation.system.repository;

import com.movie.reservation.system.model.VerifyOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerifyOtpRepo extends JpaRepository<VerifyOtp, String> {
    
    Optional<VerifyOtp> findByEmail(String email);
    
    void deleteByEmail(String email);
    
    void deleteByCreatedAtBefore(LocalDateTime cutoffTime);
}
