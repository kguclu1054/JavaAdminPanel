package com.example.demo.hash;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234"; // Buraya mevcut şifreni yaz
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashlenmiş Şifre: " + hashedPassword);
    }
}
