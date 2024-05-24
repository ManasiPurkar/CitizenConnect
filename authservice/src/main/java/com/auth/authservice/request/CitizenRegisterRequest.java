package com.auth.authservice.request;

import lombok.Getter;

@Getter
public class CitizenRegisterRequest {
    private String firstname;

    private String lastname;

    private String mobile_no;

    private String email;

    private String areaCode;
}
