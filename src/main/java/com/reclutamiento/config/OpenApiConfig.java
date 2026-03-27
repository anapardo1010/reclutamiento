package com.reclutamiento.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Reclutamiento")
                        .version("v1")
                        .description("API para gestionar candidatos")
                        .contact(new Contact().name("Equipo Reclutamiento").email("soporte@example.com"))
                );
    }
}

