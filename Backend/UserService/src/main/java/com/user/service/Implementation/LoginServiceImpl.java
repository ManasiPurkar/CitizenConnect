package com.user.service.Implementation;

import com.user.service.DTOs.ApiException;
import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Repositories.CitizenRepository;
import com.user.service.Repositories.NagarsevakRepository;
import com.user.service.Repositories.UserRepository;
import com.user.service.Services.LoginService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private NagarsevakRepository nagarsevakRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Pair<Boolean,LoginResponseDTO> login(LoginDTO user)
    {
        Boolean status=false;
        Optional<Users> gotuser = userRepository.findById(user.getEmail());

        if (gotuser.isPresent()) {

            if (passwordEncoder.matches(user.getPassword(),gotuser.get().getPassword())) {
                status=true;
                int userid;
                System.out.println("role "+gotuser.get().getRole());
                if(Objects.equals(gotuser.get().getRole(), "ROLE_CITIZEN"))
                {
                    userid=citizenRepository.findCitizenByEmail(gotuser.get().getEmail());
                }
                else if(Objects.equals(gotuser.get().getRole(), "ROLE_NAGARSEVAK"))
                {
                    userid=nagarsevakRepository.findNagarsevakByEmail(gotuser.get().getEmail());
                }

                else if(Objects.equals(gotuser.get().getRole(), "ROLE_ADMIN"))
                {
                    userid=0;
                }
                else {
                    throw new APIRequestException("Wrong Role!");
                }
                LoginResponseDTO loginRespDTO=LoginResponseDTO.builder()
                        .email(gotuser.get().getEmail())
                        .user_id(userid)
                        .role(gotuser.get().getRole())
                        .build();
                return Pair.of(status,loginRespDTO); // Login successful
            } else {
                throw new APIRequestException("Incorrect Username or Password");
            }
        }
        else {
            throw new APIRequestException("User not found");
        }
    }
}
