package com.user.service.Services;

import com.user.service.DTOs.UserRequest;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Nagarsevak;

public interface CitizenService {
    Citizen registerCitizen(UserRequest gotcitizen);
}
