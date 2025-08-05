package com.digital.evidence.api.service;

import com.digital.evidence.api.dto.KeycloakTokenRequestDTO;
import com.digital.evidence.api.dto.KeycloakTokenResponseDTO;

public interface KeycloakAuthApiService {
    KeycloakTokenResponseDTO authenticate(KeycloakTokenRequestDTO requestDTO);
}
