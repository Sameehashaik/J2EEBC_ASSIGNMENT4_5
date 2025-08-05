package com.digital.evidence.auth.keycloack.service;

import com.digital.evidence.auth.keycloack.KeycloakTokenRequestModel;
import com.digital.evidence.auth.keycloack.KeycloakTokenResponseModel;

public interface KeycloakAuthService {
    KeycloakTokenResponseModel getToken(KeycloakTokenRequestModel request);

}
