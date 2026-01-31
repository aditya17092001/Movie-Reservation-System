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
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID theater_id;

    private String theater_name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
