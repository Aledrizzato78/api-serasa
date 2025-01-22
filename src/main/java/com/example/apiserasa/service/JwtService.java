package com.example.apiserasa.service;

import com.example.apiserasa.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.util.Base64;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;

        // Converte a chave secreta armazenada no application.properties para bytes e cria a chave SecretKey
        byte[] keyBytes = Base64.getDecoder().decode(jwtConfig.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Método para gerar um token JWT baseado nos detalhes do usuário
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Define o "usuário" no token
                .setIssuedAt(new Date())  // Define a data de criação do token
                .setIssuer(jwtConfig.getIssuer())  // Define o emissor do token
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))  // Define tempo de expiração
                .signWith(secretKey, SignatureAlgorithm.HS256)  // Assina o token com a chave segura
                .compact();
    }

    // Método para extrair o nome de usuário (subject) do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método para validar se um token pertence ao usuário e se ainda é válido
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Método para verificar se o token já expirou
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Método genérico para extrair uma informação específica do token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Método para extrair todas as informações do token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
