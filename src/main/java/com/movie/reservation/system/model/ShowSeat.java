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
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID show_seat_id;
    
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Shows show;
    
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    
    public enum SeatStatus {
        AVAILABLE, BOOKED, BLOCKED
    }
}
