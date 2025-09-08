package com.korit.backend.controller;

import com.korit.backend.security.model.PrincipalUser;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/principal")
public class PrincipalController {

    @GetMapping
    public ResponseEntity<?> principal(@AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(principalUser);
    }
}
