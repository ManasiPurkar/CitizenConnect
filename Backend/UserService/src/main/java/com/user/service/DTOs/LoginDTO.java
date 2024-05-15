package com.user.service.DTOs;

import lombok.*;

@Data
@Builder
public class LoginDTO {
    private String email;
    private String password;
}
