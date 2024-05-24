package com.auth.authservice.service;

import com.auth.authservice.client.UserServiceClient;
import com.auth.authservice.dto.CitizenRegisterDto;
import com.auth.authservice.dto.TokenDto;
import com.auth.authservice.dto.UserDto;
import com.auth.authservice.exc.WrongCredentialsException;
import com.auth.authservice.request.CitizenRegisterRequest;
import com.auth.authservice.request.LoginRequest;
import com.auth.authservice.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public TokenDto login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authenticate.isAuthenticated()) {
            UserDto user=userServiceClient.getUserByUsername(request.getUsername()).getBody();
            if(user!=null) {
                Integer user_id = user.getUser_id();
                String name = user.getName();
                String role = user.getRole();
                String email = user.getEmail();

//            Integer user_id=userServiceClient.getUserByUsername(request.getUsername()).getBody().getUser_id();
//            String name=userServiceClient.getUserByUsername(request.getUsername()).getBody().getName();
//            String role=userServiceClient.getUserByUsername(request.getUsername()).getBody().getRole();
//            String email=userServiceClient.getUserByUsername(request.getUsername()).getBody().getEmail();
            return TokenDto
                    .builder()
                    .token(jwtService.generateToken(request.getUsername()))
                    .user_id(user_id)
                    .name(name)
                    .role(role)
                    .email(email)
                    .build();
            }
            else
                throw new RuntimeException("error in getting user");

        }
        else throw new WrongCredentialsException("Wrong credentials");
    }

    public CitizenRegisterDto registerCitizen(CitizenRegisterRequest request) {
        return userServiceClient.saveCitizen(request).getBody();
    }
    public String registerAdmin(RegisterRequest request) {
        return userServiceClient.addAdmin(request).getBody();
    }
}
