package com.user.service.DTOs;

import com.user.service.Entities.Area;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
public class UserResponse {

    private String email;

    private String role;

    private String firstname;

    private String lastname;

    private String mobile_no;

    private String area_name;

    private String areaCode;


}
