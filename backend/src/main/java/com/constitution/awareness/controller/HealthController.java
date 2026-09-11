package com.constitution.awareness.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = "${app.cors.allowed-origins:http://localhost:5173}"
)
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> healthCheck() {

        Map<String, String> response = new HashMap<>();

        response.put("status", "UP");
        response.put("message", "Constitution Awareness API is running");

        return response;
    }
}