package com.example.apiserasa.service;

import com.example.apiserasa.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    @Value("${spring.security.user.name}")
    private String defaultUsername;

    @Value("${spring.security.user.password}")
    private String defaultPassword;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String autenticarUsuario(String username, String password) {
        if (defaultUsername.equals(username) && defaultPassword.equals(password)) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Usuário ou senha inválidos!");
    }
}
