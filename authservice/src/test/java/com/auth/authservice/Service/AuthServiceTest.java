package com.auth.authservice.Service;


import com.auth.authservice.client.UserServiceClient;
import com.auth.authservice.dto.CitizenRegisterDto;
import com.auth.authservice.dto.TokenDto;
import com.auth.authservice.dto.UserDto;
import com.auth.authservice.exc.WrongCredentialsException;
import com.auth.authservice.request.CitizenRegisterRequest;
import com.auth.authservice.request.LoginRequest;
import com.auth.authservice.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.auth.authservice.service.JwtService;
import com.auth.authservice.service.AuthService;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest("username", "password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        UserDto userResponse = new UserDto();
        userResponse.setUser_id(1);
        userResponse.setName("Test User");
        userResponse.setRole("USER");
        userResponse.setEmail("testuser@example.com");

        when(userServiceClient.getUserByUsername(request.getUsername())).thenReturn(ResponseEntity.ok(userResponse));
        when(jwtService.generateToken(request.getUsername())).thenReturn("jwt-token");

        TokenDto tokenDto = authService.login(request);

        assertNotNull(tokenDto);
        assertEquals("jwt-token", tokenDto.getToken());
        assertEquals(1, tokenDto.getUser_id());
        assertEquals("Test User", tokenDto.getName());
        assertEquals("USER", tokenDto.getRole());
        assertEquals("testuser@example.com", tokenDto.getEmail());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userServiceClient, times(1)).getUserByUsername(request.getUsername());
        verify(jwtService, times(1)).generateToken(request.getUsername());
    }


    @Test
    void testLogin_Failure() {
        LoginRequest request = new LoginRequest("username", "wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new WrongCredentialsException("Wrong credentials"));

        assertThrows(WrongCredentialsException.class, () -> authService.login(request));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userServiceClient, never()).getUserByUsername(anyString());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void testRegisterCitizen() {
        CitizenRegisterRequest request = new CitizenRegisterRequest();
        request.setEmail("test@example.com");
        request.setFirstname("John");
        request.setLastname("Doe");
        // Set other fields if required

        CitizenRegisterDto citizenRegisterDto = new CitizenRegisterDto();
        citizenRegisterDto.setEmail("test@example.com");
        citizenRegisterDto.setFirstname("John");
        citizenRegisterDto.setLastname("Doe");
        // Set other fields if required

        when(userServiceClient.saveCitizen(request)).thenReturn(ResponseEntity.ok(citizenRegisterDto));

        CitizenRegisterDto result = authService.registerCitizen(request);

        assertNotNull(result);
        assertEquals(citizenRegisterDto, result);

        verify(userServiceClient, times(1)).saveCitizen(request);
    }


    @Test
    void testRegisterAdmin() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("admin@example.com");
        request.setPassword("admin");
        when(userServiceClient.addAdmin(request)).thenReturn(ResponseEntity.ok("Admin Successfully registered"));

        String result = authService.registerAdmin(request);

        assertEquals("Admin Successfully registered", result);

        verify(userServiceClient, times(1)).addAdmin(request);
    }
}
