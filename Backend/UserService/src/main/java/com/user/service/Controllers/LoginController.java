package com.user.service.Controllers;

import com.user.service.DTOs.LoginDTO;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Services.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Validated
    @PostMapping("/")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
    try
    {
        return ResponseEntity.ok(loginService.login(loginDTO));
    }
    catch (Exception ex) {
        if (ex instanceof APIRequestException) {
            throw new APIRequestException(ex.getMessage());
        } else
            throw new APIRequestException("Error while login", ex.getMessage());
    }
    }

    }
