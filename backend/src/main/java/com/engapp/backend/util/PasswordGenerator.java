package com.engapp.backend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "taro1234#";
        String encoded = encoder.encode(rawPassword);

        System.out.println("RAW: " + rawPassword);
        System.out.println("ENCODED: " + encoded);
    }
}