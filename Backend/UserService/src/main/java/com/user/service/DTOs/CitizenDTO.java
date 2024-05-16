package com.user.service.DTOs;

import com.user.service.Entities.Area;
import com.user.service.Entities.Complaints;
import com.user.service.Entities.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CitizenDTO {


    private String name;

    private String mobile_no;

    private String email;

    private String areaCode;

    private String area_name;

    private List<Complaints> complaintsList;
}
