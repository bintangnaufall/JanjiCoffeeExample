package com.bintangnaufal.uas;

import java.util.Random;

public class OTPGenerator {

    public static String generateOTP(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // Angka 0-9
        }
        return otp.toString();
    }
}
