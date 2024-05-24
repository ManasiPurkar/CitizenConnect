package com.auth.authservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private String password;
    private String role;
    private String email;
    private Integer user_id;
    private String name;
}
