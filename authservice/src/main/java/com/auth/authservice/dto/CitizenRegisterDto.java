package com.auth.authservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenRegisterDto {

    private String email;

    private String role;

    private String firstname;

    private String lastname;

    private String mobile_no;

    private String area_name;

    private String areaCode;
}
