package com.example.apiserasa.service;

import com.example.apiserasa.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String autenticarUsuario(String username, String password) {
        // Aqui você deve verificar usuário/senha no banco (mockado no exemplo)
        if ("admin".equals(username) && "password".equals(password)) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Usuário ou senha inválidos!");
    }
}
