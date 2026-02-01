package com.movie.reservation.system.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@IdClass(BookingSeat.BookingSeatId.class)
public class BookingSeat {
    @Id
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;
    
    @Data
    public static class BookingSeatId implements Serializable {
        private Booking booking;
        private ShowSeat showSeat;
    }
}
