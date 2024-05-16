package com.user.service.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    @NotBlank(message = "Old Password cannot be blank")
    private String oldPassword;
    @NotBlank(message = "New Password cannot be blank")
    private String newPassword;
    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;
    @Email(message = "wrong email format")
    private String email;
}
