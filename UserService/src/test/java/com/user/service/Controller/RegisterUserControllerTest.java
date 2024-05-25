package com.user.service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.Controllers.RegisterUserController;
import com.user.service.DTOs.UserRequest;
import com.user.service.DTOs.UserResponse;
import com.user.service.Entities.Area;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Services.AdminService;
import com.user.service.Services.CitizenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterUserController.class)
@ExtendWith(MockitoExtension.class)
public class RegisterUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private CitizenService citizenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addNagarsevak_Success() throws Exception {
        UserRequest nagarsevakRequest = UserRequest.builder()
                .email("nagarsevak@example.com")
                .mobile_no("1234567890")
                .firstname("Nagarsevak")
                .lastname("Test")
                .areaCode("AREA123")
                .build();

        Area area = new Area();
        area.setAreaCode("AREA123");
        area.setName("Test Area");

        Users user = new Users();
        user.setEmail("nagarsevak@example.com");
        user.setRole("ROLE_NAGARSEVAK");

        Nagarsevak nagarsevak = Nagarsevak.builder()
                .user(user)
                .area(area)
                .firstname("Nagarsevak")
                .lastname("Test")
                .mobile_no("1234567890")
                .build();

        when(adminService.registerNagarsevak(nagarsevakRequest)).thenReturn(nagarsevak);

        mockMvc.perform(post("/register/Nagarsevak")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nagarsevakRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("nagarsevak@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_NAGARSEVAK"))
                .andExpect(jsonPath("$.areaCode").value("AREA123"))
                .andExpect(jsonPath("$.area_name").value("Test Area"))
                .andExpect(jsonPath("$.firstname").value("Nagarsevak"))
                .andExpect(jsonPath("$.lastname").value("Test"))
                .andExpect(jsonPath("$.mobile_no").value("1234567890"));
    }

    @Test
    void addCitizen_Success() throws Exception {
        UserRequest citizenRequest = UserRequest.builder()
                .email("citizen@example.com")
                .mobile_no("1234567890")
                .firstname("Citizen")
                .lastname("Test")
                .areaCode("AREA123")
                .build();

        Area area = new Area();
        area.setAreaCode("AREA123");
        area.setName("Test Area");

        Users user = new Users();
        user.setEmail("citizen@example.com");
        user.setRole("ROLE_CITIZEN");

        Citizen citizen = Citizen.builder()
                .user(user)
                .area(area)
                .firstname("Citizen")
                .lastname("Test")
                .mobile_no("1234567890")
                .build();

        when(citizenService.registerCitizen(citizenRequest)).thenReturn(citizen);

        mockMvc.perform(post("/register/Citizen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citizenRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("citizen@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_CITIZEN"))
                .andExpect(jsonPath("$.areaCode").value("AREA123"))
                .andExpect(jsonPath("$.area_name").value("Test Area"))
                .andExpect(jsonPath("$.firstname").value("Citizen"))
                .andExpect(jsonPath("$.lastname").value("Test"))
                .andExpect(jsonPath("$.mobile_no").value("1234567890"));
    }


}
