package com.hubspot.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl {
    @Autowired
    private EmailServiceImpl emailService;

    private static final int OTP_LENGTH = 6;

    public String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public void sendOtpByEmail(String email, String otp) {
        String subject = "Forget Password OTP";
        String body = "Your OTP for password reset is: " + otp;

        emailService.sendOtpEmail(email, subject, body);
    }

}
