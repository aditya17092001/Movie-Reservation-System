package com.movie.reservation.system.util;

import java.security.SecureRandom;

public class GenerateOTP {
    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp() {
        return String.format("%06d", random.nextInt(1_000_000));
    }  
}
