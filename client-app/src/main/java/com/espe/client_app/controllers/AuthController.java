package com.espe.client_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@RestController
@RequestMapping("/oauth2")
public class AuthController {

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> exchangeCodeForToken(@RequestParam Map<String, String> requestParams) {
        // String authServerTokenUrl = "http://127.0.0.1:9000/oauth2/token";
        String authServerTokenUrl = "http://172.190.133.209:9000/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestParams, headers);
        ResponseEntity<Map> response = restTemplate.exchange(authServerTokenUrl, HttpMethod.POST, entity, Map.class);

        return ResponseEntity.ok(response.getBody());
    }
}