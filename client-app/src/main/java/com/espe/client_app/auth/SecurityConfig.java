//package com.espe.client_app.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configurar CORS
//                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para API
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin sesiones
//                .authorizeHttpRequests(authHttp -> authHttp
//                        .requestMatchers("/authorized", "/oauth2/**").permitAll() // Permitir autenticación OAuth2
//                        .requestMatchers(HttpMethod.GET, "/list").hasAnyRole("USER", "ADMIN") // Acceso a usuarios autenticados
//                        .requestMatchers(HttpMethod.POST, "/create").hasRole("ADMIN") // Solo ADMIN puede crear
//                        .anyRequest().authenticated()) // Todas las demás rutas requieren autenticación
//                .oauth2Login(withDefaults()) // RESTAURADO: Permitir OAuth2 Login
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))); // Configuración JWT
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowedOrigins(List.of("http://127.0.0.1:5173", "http://localhost:5173")); // Permitir frontend
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
//        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Encabezados esenciales
//        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // Permitir al frontend leer headers
//        config.setAllowCredentials(true); // Permitir credenciales (tokens y cookies)
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Asegurar que se extraigan los roles correctamente
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
//}
package com.espe.client_app.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configurar CORS
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para API
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin sesiones
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers("/authorized", "/oauth2/**").permitAll() // Permitir autenticación OAuth2
                        .requestMatchers(HttpMethod.GET, "/list").hasAnyRole("USER", "ADMIN") // Acceso a usuarios autenticados
                        .requestMatchers(HttpMethod.POST, "/create").hasRole("ADMIN") // Solo ADMIN puede crear
                        .anyRequest().authenticated()) // Todas las demás rutas requieren autenticación
                .oauth2Login(withDefaults()) // Permitir OAuth2 Login
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))); // Configuración JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://172.190.133.209:5173", "http://172.190.133.209:5173")); // Permitir frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Encabezados esenciales
        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // Permitir al frontend leer headers
        config.setAllowCredentials(true); // Permitir credenciales (tokens y cookies)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Prefijo estándar de Spring Security
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Extraer los roles desde el claim "roles"

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
