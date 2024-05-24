package com.user.service.Services;

import com.user.service.DTOs.ChangePasswordDTO;
import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Users;
import org.apache.commons.lang3.tuple.Pair;

public interface LoginService {
    LoginResponseDTO getUserByUsername(String username);
    Boolean changePassword(ChangePasswordDTO changePasswordDTO);
}
