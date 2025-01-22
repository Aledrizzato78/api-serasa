package com.example.apiserasa.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin";  // Altere conforme necessário
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Senha criptografada: " + encodedPassword);
    }
}
