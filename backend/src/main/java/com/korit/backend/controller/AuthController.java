package com.korit.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin() {
        return ResponseEntity.ok().build();
    }
}
