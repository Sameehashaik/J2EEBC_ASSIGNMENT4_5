package com.digital.evidence.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@GetMapping("/login")
    public String login() {
        return "user/login";
    }
	
	@GetMapping("/logout-keycloak")
	public void logoutKeycloak(HttpServletResponse response) throws IOException {
	    response.sendRedirect("http://localhost:3128/realms/digital-evidence/protocol/openid-connect/logout?redirect_uri=http://localhost:8080/auth/login");
	}

}
