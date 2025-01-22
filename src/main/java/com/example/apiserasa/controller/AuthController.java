package com.example.apiserasa.controller;

import com.example.apiserasa.dto.AuthRequest;
import com.example.apiserasa.dto.AuthResponse;
import com.example.apiserasa.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> autenticar(@RequestBody AuthRequest authRequest) {
        try {
            String token = authService.autenticarUsuario(authRequest.getUsername(), authRequest.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Usuário ou senha inválidos"));
        }
    }
}
