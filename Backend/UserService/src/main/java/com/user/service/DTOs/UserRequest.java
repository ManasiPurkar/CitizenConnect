package com.user.service.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String firstname;

    private String lastname;

    private String mobile_no;

    private String email;

    private String areaCode;

}
