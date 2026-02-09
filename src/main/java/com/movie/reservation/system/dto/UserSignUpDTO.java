package com.movie.reservation.system.dto;

import lombok.Data;

@Data   
public class UserSignUpDTO {
    private String email;
    private String password;
    private String phoneNo;
    private String name;
    private String gender;
}
