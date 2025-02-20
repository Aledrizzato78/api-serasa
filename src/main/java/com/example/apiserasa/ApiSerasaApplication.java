package com.example.apiserasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.apiserasa.config")  // Adiciona suporte às configurações de propriedades
@OpenAPIDefinition(info = @Info(
        title = "API Serasa",
        version = "1.0",
        description = "API REST para cadastro de pessoas com score e endereço"
))
public class ApiSerasaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSerasaApplication.class, args);
        System.out.println("API Serasa está rodando em http://localhost:8080");
    }
}
