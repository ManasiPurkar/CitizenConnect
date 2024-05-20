package com.user.service.Controllers;


import com.user.service.DTOs.ChangePasswordDTO;
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
@RequestMapping("/user")
//@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Validated
    @PostMapping("/login")
    public ResponseEntity<Pair<String, LoginResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {

            System.out.println("login" + loginDTO.getEmail()  + loginDTO.getPassword());
            Pair<Boolean, LoginResponseDTO> result = loginService.login(loginDTO);
            if (result.getKey()) {
                System.out.println("login success");
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
    @Validated
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            Boolean result = loginService.changePassword(changePasswordDTO);
            if (result) {
                return ResponseEntity.ok("Password changed Successfully");
            }
            return ResponseEntity.ok("Error in changing password");
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while changing password", ex.getMessage());
        }
    }

    }
