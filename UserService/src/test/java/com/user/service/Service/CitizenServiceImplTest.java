package com.user.service.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.user.service.Exceptions.APIRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.user.service.Repositories.CitizenRepository;
import com.user.service.Repositories.UserRepository;
import com.user.service.Repositories.AreaRepository;
import com.user.service.Implementation.EmailService;
import com.user.service.Implementation.CitizenServiceImpl;
import com.user.service.DTOs.UserRequest;
import com.user.service.Entities.Area;
import com.user.service.Entities.Users;
import com.user.service.Entities.Citizen;

@ExtendWith(MockitoExtension.class)
class CitizenServiceImplTest {

    @Mock
    private CitizenRepository citizenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private EmailService emailService;


    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CitizenServiceImpl citizenService;

    @Test
    void registerCitizen_Success() {
        // Arrange
        UserRequest userRequest = UserRequest.builder()
                .email("test@example.com")
                .mobile_no("1234567890")
                .firstname("John")
                .lastname("Doe")
                .areaCode("AREA123")
                .build();

        Area area = new Area();
        area.setAreaCode("AREA123");
        area.setName("Test Area");

        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole("ROLE_CITIZEN");

        Citizen citizen = Citizen.builder()
                .mobile_no("1234567890")
                .firstname("John")
                .lastname("Doe")
                .area(area)
                .user(user)
                .active(true)
                .build();

        when(areaRepository.findById("AREA123")).thenReturn(Optional.of(area));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(citizenRepository.save(any(Citizen.class))).thenReturn(citizen);
        when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

        // Act
        Citizen result = citizenService.registerCitizen(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("Test Area", result.getArea().getName());
        assertEquals("test@example.com", result.getUser().getEmail());
        assertTrue(result.getActive());
        verify(citizenRepository).save(any(Citizen.class));
        verify(emailService).sendEmail(anyString(), anyString(), anyString());
    }


    @Test
    void registerCitizen_AreaNotFound() {
        // Arrange
        UserRequest userRequest = UserRequest.builder()
                .email("test@example.com")
                .mobile_no("1234567890")
                .firstname("John")
                .lastname("Doe")
                .areaCode("UNKNOWN")
                .build();

        when(areaRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        // Act & Assert
        APIRequestException exception = assertThrows(APIRequestException.class, () -> citizenService.registerCitizen(userRequest));
        assertEquals("Area not found", exception.getMessage());
        verifyNoInteractions(userRepository, citizenRepository, emailService);
    }
}
