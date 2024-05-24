package com.auth.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String token;
    private Integer user_id;
    private String name;
    private String email;
    private String role;
}
