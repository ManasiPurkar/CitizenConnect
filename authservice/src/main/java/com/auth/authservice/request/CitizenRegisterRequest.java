package com.auth.authservice.request;

import lombok.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CitizenRegisterRequest {
    private String firstname;

    private String lastname;

    private String mobile_no;

    private String email;

    private String areaCode;
}
