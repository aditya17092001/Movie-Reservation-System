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
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID seat_id;
    
    private int row;
    private int col;
    
    @Enumerated(EnumType.STRING)
    private SeatType type;
    
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
    
    public enum SeatType {
        NORMAL, PREMIUM, RECLINER
    }
}
