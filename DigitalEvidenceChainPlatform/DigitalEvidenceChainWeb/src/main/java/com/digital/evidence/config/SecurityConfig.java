package com.digital.evidence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.digital.evidence.auth.AppUserDetailsService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final AppUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(AppUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/login", "/login/oauth2/code/**",
                                 "/users/create", "/users", "/css/**", "/js/**",
                                 "/error", "/favicon.ico", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/evidence/all", true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/auth/login")
                .userInfoEndpoint(userInfo -> userInfo
                    .oidcUserService(oidcUserService())
                )
                .defaultSuccessUrl("/evidence/all", true)
                .failureHandler((request, response, exception) -> {
                    log.error("OAuth2 login failed", exception);
                    response.sendRedirect("/auth/login?error=oauth2&details=" + exception.getClass().getSimpleName());
                })
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            )
            .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

    // OAuth2 configuration is now in separate OAuth2Config class

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();
        return (userRequest) -> {
            try {
                log.info("Processing OIDC user request for client: {}", userRequest.getClientRegistration().getRegistrationId());
                
                OidcUser oidcUser = delegate.loadUser(userRequest);
             //   log.info("OIDC User loaded successfully: {}", oidcUser.getAttribute("preferred_username"));
                log.debug("OIDC User attributes: {}", oidcUser.getAttributes());

                Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

                // Extract roles from realm_access
                Map<String, Object> realmAccess = oidcUser.getAttribute("realm_access");
                log.info("realm_access: {}", realmAccess);
                
                if (realmAccess != null) {
                    Object rolesObj = realmAccess.get("roles");
                    log.info("roles object: {}", rolesObj);
                    
                    if (rolesObj instanceof List<?>) {
                        List<?> roles = (List<?>) rolesObj;
                        for (Object roleObj : roles) {
                            if (roleObj instanceof String) {
                                String role = (String) roleObj;
                                // Skip default Keycloak roles
                                if (!role.startsWith("default-") && 
                                    !role.equals("offline_access") && 
                                    !role.equals("uma_authorization")) {
                                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                                    log.info("Mapped role: ROLE_{}", role.toUpperCase());
                                }
                            }
                        }
                    }
                }

                // Always assign default role if no meaningful roles found
                if (mappedAuthorities.isEmpty()) {
                    log.info("No application roles found, assigning default ROLE_USER");
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                log.info("Final mapped authorities: {}", mappedAuthorities);

                return new org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser(
                        mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
                        
            } catch (Exception ex) {
                log.error("Exception in oidcUserService", ex);
                // Re-throw as RuntimeException so Spring Security can handle it
                throw new RuntimeException("Failed to process OIDC user: " + ex.getMessage(), ex);
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}