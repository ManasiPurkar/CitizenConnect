package com.user.service.Implementation;

import com.user.service.DTOs.ApiException;
import com.user.service.DTOs.LoginDTO;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Repositories.UserRepository;
import com.user.service.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDTO user)
    {
        Optional<Users> gotuser = userRepository.findById(user.getEmail());
        if (gotuser.isPresent()) {
            if (passwordEncoder.matches(gotuser.get().getPassword(), user.getPassword())) {
                return "Successfully Logged In"; // Login successful
            } else {
                throw new APIRequestException("Incorrect Username or Password");
            }
        }
        else {
            throw new APIRequestException("User not found");
        }
    }
}
