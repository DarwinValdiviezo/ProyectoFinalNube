//package com.espe.micro_registro.config;
//
//import feign.RequestInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
//public class FeignClientConfig {
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            Object principal = SecurityContextHolder.getContext().getAuthentication();
//            if (principal instanceof JwtAuthenticationToken jwtToken) {
//                String tokenValue = jwtToken.getToken().getTokenValue();
//                requestTemplate.header("Authorization", "Bearer " + tokenValue);
//            }
//        };
//    }
//}
