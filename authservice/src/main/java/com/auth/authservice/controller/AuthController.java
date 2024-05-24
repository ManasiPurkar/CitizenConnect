package com.auth.authservice.controller;

import com.auth.authservice.dto.CitizenRegisterDto;
import com.auth.authservice.dto.TokenDto;
import com.auth.authservice.request.CitizenRegisterRequest;
import com.auth.authservice.request.LoginRequest;
import com.auth.authservice.request.RegisterRequest;
import com.auth.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register/Citizen")
    public ResponseEntity<CitizenRegisterDto> registerCitizen(@RequestBody CitizenRegisterRequest request) {
        return ResponseEntity.ok(authService.registerCitizen(request));
    }
    @PostMapping("/register/Admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }
}
