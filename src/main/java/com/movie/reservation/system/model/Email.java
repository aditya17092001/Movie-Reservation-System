package com.movie.reservation.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String recipient;
    private String msgBody;
    private String subject;
    private String otp;
}
