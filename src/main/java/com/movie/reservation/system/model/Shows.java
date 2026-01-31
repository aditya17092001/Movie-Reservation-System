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
public class Shows {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID show_id;

    private LocalDateTime show_start_time;
    private LocalDateTime show_end_time;
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
}
