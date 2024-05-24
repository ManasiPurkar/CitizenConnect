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
    @GetMapping("/{username}")
    public LoginResponseDTO getUserbyUsername(@PathVariable String username) {
        try {

//            System.out.println("login" + loginDTO.getEmail()  + loginDTO.getPassword());
            return loginService.getUserByUsername(username);
//            if (result.getKey()) {
//                System.out.println("login success");
//                return ResponseEntity.ok(Pair.of("Logged in Successfully", result.getValue()));
//            }
//            else
//            {
//                throw new APIRequestException("Login Fails!");
//            }
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while getting user", ex.getMessage());
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
