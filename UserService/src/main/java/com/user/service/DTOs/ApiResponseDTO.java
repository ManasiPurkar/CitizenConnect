package com.user.service.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {
//    It means that object can refer to any object since all classes implicitly inherit from Object
    private Object object;
    private boolean success;
}
