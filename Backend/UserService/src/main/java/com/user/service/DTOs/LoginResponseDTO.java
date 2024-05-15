package com.user.service.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String email;
    private Integer user_id;
    private String role;
    private String name;
}
