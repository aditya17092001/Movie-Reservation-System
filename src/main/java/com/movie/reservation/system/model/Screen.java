package com.movie.reservation.system.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID screen_id;
    
    private String screen_name;
    private int total_seats;
    
    @Enumerated(EnumType.STRING)
    private ScreenStatus status;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;
    
    public enum ScreenStatus {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}
