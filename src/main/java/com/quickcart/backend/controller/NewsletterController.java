package com.quickcart.backend.controller;

import com.quickcart.backend.service.NewsletterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsletterController {

    private final NewsletterService service;

    public NewsletterController(NewsletterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> body) {

        String email = body.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        String response = service.subscribe(email);

        return ResponseEntity.ok(response);
    }
}
