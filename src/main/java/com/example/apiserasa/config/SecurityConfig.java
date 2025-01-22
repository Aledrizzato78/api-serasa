package com.example.apiserasa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Libera autenticação
                        .requestMatchers("/h2-console/**").permitAll()  // Libera H2 Console
                        .requestMatchers("/api-docs/**", "/swagger-ui/**").permitAll()  // Libera Swagger
                        .requestMatchers("/api/pessoas/**").permitAll()  // Libera todas as operações de pessoas
                        .requestMatchers("/error").permitAll()  // Libera página de erro
                        .anyRequest().permitAll()  // Libera tudo temporariamente para testes
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Libera o acesso ao console H2 no navegador
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // Configuração temporária de usuários em memória para testes
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails normalUser = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    // Bean para codificação de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
