package com.example.apiserasa.service;

import com.example.apiserasa.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final JwtUtil jwtUtil;

    @Value("${spring.security.user.name}")
    private String defaultUsername;

    @Value("${spring.security.user.password}")
    private String defaultPassword;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String autenticarUsuario(String username, String password) {
        logger.info("Tentativa de autenticação com usuário: {}", username);
        logger.info("Credenciais esperadas - username: {}, password: {}", defaultUsername, defaultPassword);

        if (defaultUsername.equals(username) && defaultPassword.equals(password)) {
            logger.info("Autenticação bem-sucedida para o usuário: {}", username);
            return jwtUtil.generateToken(username);
        }

        logger.warn("Falha na autenticação para o usuário: {}", username);
        throw new RuntimeException("Usuário ou senha inválidos!");
    }
}
