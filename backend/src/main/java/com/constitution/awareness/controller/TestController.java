package com.constitution.awareness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/citizen/test")
    public String citizenTest() {
        return "Citizen access granted";
    }

    @GetMapping("/educator/test")
    public String educatorTest() {
        return "Educator access granted";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin access granted";
    }
}