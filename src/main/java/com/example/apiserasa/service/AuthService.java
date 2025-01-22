package com.example.apiserasa.service;

import com.example.apiserasa.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.name}")
    private String defaultUsername;

    @Value("${spring.security.user.password}")
    private String encodedPassword;  // Senha criptografada armazenada no properties

    public AuthService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Autentica o usuário verificando nome de usuário e senha criptografada.
     *
     * @param username Nome de usuário informado
     * @param password Senha em texto plano informada
     * @return Token JWT em caso de sucesso
     */
    public String autenticarUsuario(String username, String password) {
        logger.info("Tentativa de autenticação com usuário: {}", username);

        // Verifica se o nome de usuário é válido
        if (!defaultUsername.equals(username)) {
            logger.warn("Usuário inválido: {}", username);
            throw new RuntimeException("Usuário ou senha inválidos!");
        }

        // Valida a senha criptografada usando BCrypt
        if (!passwordEncoder.matches(password, encodedPassword)) {
            logger.warn("Falha de autenticação para o usuário: {}", username);
            throw new RuntimeException("Usuário ou senha inválidos!");
        }

        logger.info("Autenticação bem-sucedida para o usuário: {}", username);

        // Gera e retorna o token JWT para o usuário autenticado
        return jwtUtil.generateToken(username);
    }
}
