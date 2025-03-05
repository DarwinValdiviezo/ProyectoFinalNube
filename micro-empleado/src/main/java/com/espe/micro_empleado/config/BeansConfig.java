//package com.espe.msvc.usuarios.msvc_usuarios.config;
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
//        cors.addAllowedHeader("*");
//        cors.addAllowedMethod("*");
//        cors.setAllowCredentials(true);
//        cors.setAllowedOrigins(Arrays.asList(
//                "http://:5173",
//                "http://localhost:5173", // Añadir esta URL u otra que necesites
//                "http://frontend:5173",// Otro dominio permitido
//                "http://127.0.0.1:5173",
//                "http://localhost:5173"
//        ));
//        source.registerCorsConfiguration("/**", cors);
//        return source;
//    }
//}