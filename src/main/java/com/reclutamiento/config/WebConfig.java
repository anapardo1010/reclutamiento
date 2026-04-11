package com.reclutamiento.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS (Cross-Origin Resource Sharing)
 *
 * ¿Qué es CORS?
 * Por seguridad, los navegadores bloquean solicitudes HTTP que vienen de un
 * origen diferente al del servidor. Por ejemplo, si tu frontend corre en
 * http://localhost:3000 y tu backend en http://localhost:8080, el navegador
 * bloquea la solicitud porque los puertos son distintos.
 *
 * Esta clase le dice al backend: "acepta solicitudes de estos orígenes".
 *
 * En producción deberías limitar allowedOrigins a los dominios reales de tu app.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")          // Aplica a todos los endpoints bajo /api/
                .allowedOrigins("*")            // Acepta solicitudes de cualquier origen (solo para desarrollo)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");           // Acepta cualquier header (ej: Content-Type)
    }
}
