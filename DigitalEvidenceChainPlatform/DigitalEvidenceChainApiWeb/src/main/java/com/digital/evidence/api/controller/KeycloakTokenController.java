package com.digital.evidence.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.evidence.api.dto.KeycloakTokenRequestDTO;
import com.digital.evidence.api.dto.KeycloakTokenResponseDTO;
import com.digital.evidence.api.service.KeycloakAuthApiService;

@RestController
@RequestMapping("/auth")
public class KeycloakTokenController {
	private final KeycloakAuthApiService keycloakAuthApiService;

    @Autowired
    public KeycloakTokenController(KeycloakAuthApiService keycloakAuthApiService) {
        this.keycloakAuthApiService = keycloakAuthApiService;
    }

    @PostMapping("/token")
    public ResponseEntity<KeycloakTokenResponseDTO> getToken(@RequestBody KeycloakTokenRequestDTO requestDTO) {
        KeycloakTokenResponseDTO tokenResponse = keycloakAuthApiService.authenticate(requestDTO);
        return ResponseEntity.ok(tokenResponse);
    }
}
