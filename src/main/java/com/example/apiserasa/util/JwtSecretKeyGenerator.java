package com.example.apiserasa.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import javax.crypto.SecretKey;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Gera uma chave segura para HS256
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Chave secreta JWT segura: " + encodedKey);
    }
}
