package com.user.service.Services;

import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Users;
import org.apache.commons.lang3.tuple.Pair;

public interface LoginService {
    Pair<Boolean, LoginResponseDTO> login(LoginDTO user);
}
