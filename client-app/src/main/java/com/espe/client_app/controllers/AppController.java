package com.espe.client_app.controllers;

import com.espe.client_app.models.Message;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Base64;

@RestController
@CrossOrigin(origins = {"http://172.190.133.209:5173", "http://172.190.133.209:5173"}, allowCredentials = "true")
public class AppController {

    @GetMapping("/list")
    public List<Message> list() {
        return Collections.singletonList(new Message("Lista de mensajes"));
    }

    @PostMapping("/create")
    public Message create(@RequestBody Message message) {
        System.out.println("Mensaje guardado: " + message);
        return message;
    }

    @GetMapping("/authorized")
    public ResponseEntity<?> authorized(@RequestParam String code) {
        // String authServerTokenUrl = "http://127.0.0.1:9000/oauth2/token";
        String authServerTokenUrl = "http://172.190.133.209:9000/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Autenticación básica en el encabezado
        String clientId = "client-app";
        String clientSecret = "1234";
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", "http://172.190.133.209:8080/authorized");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(authServerTokenUrl, HttpMethod.POST, entity, Map.class);

            if (response.getBody() != null && response.getBody().containsKey("access_token")) {
                String accessToken = (String) response.getBody().get("access_token");

                // Redirigir al frontend con el token en la URL
                String redirectUrl = "http://172.190.133.209:5173/dashboard?token=" + accessToken;
                HttpHeaders redirectHeaders = new HttpHeaders();
                redirectHeaders.setLocation(new java.net.URI(redirectUrl));
                return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "No se recibió un token válido"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Error al obtener el token", "message", e.getMessage()));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no presente");
        }

        String username = jwt.getClaim("sub"); // Identificador del usuario
        List<String> roles = jwt.getClaim("roles") != null ? jwt.getClaim("roles") : List.of(); // Extraer roles

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("roles", roles); // Incluir los roles en la respuesta

        return ResponseEntity.ok(userInfo);
    }



}
