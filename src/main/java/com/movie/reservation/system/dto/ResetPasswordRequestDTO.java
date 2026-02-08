package com.movie.reservation.system.dto;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    private String email;
    private String currentPassword;
    private String updatePassword;
}
