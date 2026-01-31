package com.movie.reservation.system.model;

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
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID city_id;
    private String city_name;
    
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
