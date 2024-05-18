package com.user.service.Services;

import com.user.service.DTOs.ApiResponseDTO;
import com.user.service.DTOs.UserRequest;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Entities.Users;

public interface AdminService {
    Nagarsevak registerNagarsevak(UserRequest gotnagarsevak);
}
