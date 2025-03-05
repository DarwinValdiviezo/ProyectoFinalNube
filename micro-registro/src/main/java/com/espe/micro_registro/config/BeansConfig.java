//package com.espe.micro_registro.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//public class BeansConfig {
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration cors = new CorsConfiguration();
//
//        // Permitir todos los encabezados
//        cors.addAllowedHeader("*");
//
//        // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
//        cors.addAllowedMethod("*");
//
//        // Permitir credenciales (necesario para enviar cookies o tokens)
//        cors.setAllowCredentials(true);
//
//        // Orígenes permitidos (frontends)
//        cors.setAllowedOrigins(Arrays.asList(
//                "http://localhost:5173", // Frontend en React (local)
//                "http://127.0.0.1:5173",  // Alternativa para el frontend (local)
//                "http://frontend:5173"    // Si usas Docker o un nombre de servicio
//        ));
//
//        // Aplicar la configuración CORS a todas las rutas
//        source.registerCorsConfiguration("/**", cors);
//
//        return source;
//    }
//}