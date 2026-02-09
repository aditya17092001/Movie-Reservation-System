package com.movie.reservation.system.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID booking_id;
    
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Shows show;
    
    private UUID user_id;
    private LocalDateTime booking_time;
    private double total_amount;
    private String status;
}
