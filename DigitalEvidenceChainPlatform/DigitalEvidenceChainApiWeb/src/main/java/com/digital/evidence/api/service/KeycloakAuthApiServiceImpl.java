package com.digital.evidence.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.evidence.api.dto.KeycloakTokenRequestDTO;
import com.digital.evidence.api.dto.KeycloakTokenResponseDTO;
import com.digital.evidence.auth.keycloack.KeycloakTokenRequestModel;
import com.digital.evidence.auth.keycloack.KeycloakTokenResponseModel;
import com.digital.evidence.auth.keycloack.service.KeycloakAuthService;

@Service
public class KeycloakAuthApiServiceImpl implements KeycloakAuthApiService {
	@Autowired
	private KeycloakAuthService keycloakAuthService;

	@Override
	public KeycloakTokenResponseDTO authenticate(KeycloakTokenRequestDTO requestDTO) {
		KeycloakTokenRequestModel requestModel = new KeycloakTokenRequestModel();
		requestModel.setGrant_type(requestDTO.getGrantType());
		requestModel.setClient_id(requestDTO.getClientId());
		requestModel.setClient_secret(requestDTO.getClientSecret());
		requestModel.setUsername(requestDTO.getUsername());
		requestModel.setPassword(requestDTO.getPassword());

		KeycloakTokenResponseModel tokenResponse = keycloakAuthService.getToken(requestModel);

		KeycloakTokenResponseDTO responseDTO = new KeycloakTokenResponseDTO();
		responseDTO.setAccessToken(tokenResponse.getAccessToken());
		responseDTO.setTokenType(tokenResponse.getTokenType());
		responseDTO.setRefreshToken(tokenResponse.getRefreshToken());
		responseDTO.setExpiresIn(tokenResponse.getExpiresIn());

		return responseDTO;
	}
}
