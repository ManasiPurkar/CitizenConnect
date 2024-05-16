package com.user.service.DTOs;

import com.user.service.Entities.Area;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintDTO {
    private String title;

    private String description;

    private String address;

    private Integer department_code;

    private Integer citizenId;

    private String areaCode;

    private String areaName;
}
