package com.movie.reservation.system.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID movie_id;

    private String movie_name;
    private int age_limit;
    private int duration;
    private String language;
}
