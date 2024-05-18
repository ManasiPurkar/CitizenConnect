package com.user.service.Controllers;

import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Implementation.PasswordGeneratorService;
import com.user.service.Repositories.UserRepository;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/initialize")
//@CrossOrigin(origins = "*")
public class AddAdmin {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/admin")
    public ResponseEntity<String> login(@RequestBody Users gotuser) {
        try {
            Users user = new Users();
            user.setEmail(gotuser.getEmail());
            String password = gotuser.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("ROLE_ADMIN");
            userRepository.save(user);
            return ResponseEntity.ok("Admin Successfully registered");
        }
        catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while login", ex.getMessage());
        }
    }

}
