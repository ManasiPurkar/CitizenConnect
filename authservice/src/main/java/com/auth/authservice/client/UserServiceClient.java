package com.auth.authservice.client;

import com.auth.authservice.dto.CitizenRegisterDto;
import com.auth.authservice.dto.UserDto;
import com.auth.authservice.request.CitizenRegisterRequest;
import com.auth.authservice.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserService")
public interface UserServiceClient {
    @PostMapping("/register/Citizen")
    ResponseEntity<CitizenRegisterDto> saveCitizen(@RequestBody CitizenRegisterRequest request);

    @PostMapping("/initialize/admin")
    ResponseEntity<String> addAdmin(@RequestBody RegisterRequest request);

    @GetMapping("/user/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable String username);
}
