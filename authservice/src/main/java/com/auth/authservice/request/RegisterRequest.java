package com.auth.authservice.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String password;
    private String email;
}
