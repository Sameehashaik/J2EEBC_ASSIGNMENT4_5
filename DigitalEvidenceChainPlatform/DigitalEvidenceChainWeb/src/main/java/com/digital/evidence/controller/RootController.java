package com.digital.evidence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
public class RootController {
	
	@PostConstruct
    public void init() {
        System.out.println("✅ RootController loaded");
    }
	
	@GetMapping("/ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }
}
