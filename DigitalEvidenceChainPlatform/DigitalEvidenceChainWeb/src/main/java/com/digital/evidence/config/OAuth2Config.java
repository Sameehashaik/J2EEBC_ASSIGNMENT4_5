package com.digital.evidence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * OAuth2 Client Configuration for Keycloak integration.
 * This is required because we're not using Spring Boot auto-configuration.
 */
@Configuration
public class OAuth2Config {
    
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
    }
    
    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistration.withRegistrationId("keycloak")
            .clientId("evidence-api-client")
            .clientSecret("sARhplVLdokPFSWFLO7vbfLHYDyVywNS")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("http://localhost:8080/login/oauth2/code/keycloak")
            .scope("openid", "profile", "email")
            .authorizationUri("http://localhost:3128/realms/digital-evidence/protocol/openid-connect/auth")
            .tokenUri("http://localhost:3128/realms/digital-evidence/protocol/openid-connect/token")
            .userInfoUri("http://localhost:3128/realms/digital-evidence/protocol/openid-connect/userinfo")
            .jwkSetUri("http://localhost:3128/realms/digital-evidence/protocol/openid-connect/certs")
            .userNameAttributeName("preferred_username")
            .clientName("Keycloak")
            .build();
    }
}