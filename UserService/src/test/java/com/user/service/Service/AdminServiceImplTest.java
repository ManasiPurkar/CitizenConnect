package com.user.service.Service;
import com.user.service.DTOs.UserRequest;
import com.user.service.Entities.Area;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Implementation.AdminServiceImpl;
import com.user.service.Repositories.AreaRepository;
import com.user.service.Repositories.NagarsevakRepository;
import com.user.service.Repositories.UserRepository;
import com.user.service.Implementation.EmailService;
import com.user.service.Implementation.PasswordGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private NagarsevakRepository nagarsevakRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordGeneratorService passwordGeneratorService;

    private UserRequest nagarsevakRequest;
    private Area area;
    private Users user;

    @BeforeEach
    void setUp() {
        nagarsevakRequest = UserRequest.builder()
                .email("test@example.com")
                .mobile_no("1234567890")
                .firstname("John")
                .lastname("Doe")
                .areaCode("1234")
                .build();

        area = new Area();
        area.setAreaCode("1234");
        area.setName("Area 1");

        user = new Users();
        user.setEmail("nagarsevak@example.com");
        user.setPassword("encodedPassword");
        user.setRole("ROLE_NAGARSEVAK");
    }

    @Test
    void registerNagarsevak_Success() {
        String generatedPassword = "generatedPassword";
        when(areaRepository.findById(nagarsevakRequest.getAreaCode())).thenReturn(Optional.of(area));
        when(passwordEncoder.encode(generatedPassword)).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(nagarsevakRepository.save(any(Nagarsevak.class))).thenAnswer(i -> i.getArgument(0));
        when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

        try (MockedStatic<PasswordGeneratorService> mockedStatic = mockStatic(PasswordGeneratorService.class)) {
            mockedStatic.when(PasswordGeneratorService::generatePassword).thenReturn(generatedPassword);

            Nagarsevak savedNagarsevak = adminService.registerNagarsevak(nagarsevakRequest);

            assertNotNull(savedNagarsevak);
            assertEquals("John", savedNagarsevak.getFirstname());
            assertEquals("Doe", savedNagarsevak.getLastname());
            assertEquals("Area 1", savedNagarsevak.getArea().getName());
            assertTrue(savedNagarsevak.getActive());

            verify(userRepository).save(any(Users.class));
            verify(nagarsevakRepository).save(any(Nagarsevak.class));
            verify(emailService).sendEmail(anyString(), anyString(), eq("nagarsevak@example.com"));
        }
    }

    @Test
    void registerNagarsevak_AreaNotFound() {
        when(areaRepository.findById(nagarsevakRequest.getAreaCode())).thenReturn(Optional.empty());

        APIRequestException exception = assertThrows(APIRequestException.class, () -> adminService.registerNagarsevak(nagarsevakRequest));
        assertEquals("Area not found", exception.getMessage());

        verify(userRepository, never()).save(any(Users.class));
        verify(nagarsevakRepository, never()).save(any(Nagarsevak.class));
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void registerNagarsevak_EmailSendingFailed() {
        String generatedPassword = "generatedPassword";

        when(areaRepository.findById(nagarsevakRequest.getAreaCode())).thenReturn(Optional.of(area));
        try (MockedStatic<PasswordGeneratorService> mockedStatic = mockStatic(PasswordGeneratorService.class)) {
            mockedStatic.when(PasswordGeneratorService::generatePassword).thenReturn(generatedPassword);

            when(passwordEncoder.encode(generatedPassword)).thenReturn("encodedPassword");
            when(userRepository.save(any(Users.class))).thenReturn(user);
            when(nagarsevakRepository.save(any(Nagarsevak.class))).thenAnswer(i -> i.getArgument(0));
            when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(false);

            APIRequestException exception = assertThrows(APIRequestException.class, () -> adminService.registerNagarsevak(nagarsevakRequest));
            assertEquals("Sending mail failed", exception.getMessage());

            verify(userRepository).save(any(Users.class));
            verify(nagarsevakRepository).save(any(Nagarsevak.class));
            verify(emailService).sendEmail(anyString(), anyString(), eq("nagarsevak@example.com"));
        }
    }
}
