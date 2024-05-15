package com.user.service.DTOs;

import com.user.service.Entities.Area;
import com.user.service.Entities.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitizenDTO {


    private String name;

    private String mobile_no;

    private String email;

    private Area area_code;

    private String area_name;


}
