package com.digital.evidence.auth.keycloack.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digital.evidence.auth.keycloack.KeycloakTokenRequestModel;
import com.digital.evidence.auth.keycloack.KeycloakTokenResponseModel;

@Service
public class KeycloakAuthServiceImpl implements KeycloakAuthService {
	private static final String TOKEN_ENDPOINT = "http://localhost:3128/realms/myrealm/protocol/openid-connect/token";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public KeycloakTokenResponseModel getToken(KeycloakTokenRequestModel request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> bodyMap = new LinkedHashMap<>();
        bodyMap.put("grant_type", request.getGrant_type());
        bodyMap.put("client_id", request.getClient_id());
        bodyMap.put("username", request.getUsername());
        bodyMap.put("password", request.getPassword());

        StringBuilder bodyBuilder = new StringBuilder();
        bodyMap.forEach((key, value) -> bodyBuilder.append(key).append("=").append(value).append("&"));
        String bodyString = bodyBuilder.toString().replaceAll("&$", "");

        HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);

        ResponseEntity<KeycloakTokenResponseModel> response =
                restTemplate.exchange(TOKEN_ENDPOINT, HttpMethod.POST, entity, KeycloakTokenResponseModel.class);

        return response.getBody();
    }
}
