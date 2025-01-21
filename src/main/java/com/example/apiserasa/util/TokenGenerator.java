package com.example.apiserasa.util;

public class TokenGenerator {
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("admin");
        System.out.println("Generated Token: " + token);
    }
}
