package com.movie.reservation.system.dto;

import lombok.Data;

@Data
public class EmailVerifyDTO {
    private String email;
    private String otp;
}
