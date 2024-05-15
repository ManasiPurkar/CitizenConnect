package com.user.service.Controllers;


import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Services.LoginService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.tuple.Pair;
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Validated
    @PostMapping("/")
    public ResponseEntity<Pair<String, LoginResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Pair<Boolean, LoginResponseDTO> result = loginService.login(loginDTO);
            if (result.getKey()) {
                return ResponseEntity.ok(Pair.of("Logged in Successfully", result.getValue()));
            }
            else
            {
                throw new APIRequestException("Login Fails!");
            }
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while login", ex.getMessage());
        }
    }

    }
