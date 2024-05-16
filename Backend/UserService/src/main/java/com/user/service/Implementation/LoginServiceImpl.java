package com.user.service.Implementation;

import com.user.service.DTOs.ApiException;
import com.user.service.DTOs.ChangePasswordDTO;
import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Nagarsevak;
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
                String name;
                System.out.println("role "+gotuser.get().getRole());
                if(Objects.equals(gotuser.get().getRole(), "ROLE_CITIZEN"))
                {
                    Citizen  citizen=citizenRepository.findCitizenByEmail(gotuser.get().getEmail());
                    userid=citizen.getCitizen_id();
                    name=citizen.getFirstname()+ " "+citizen.getLastname();
                }
                else if(Objects.equals(gotuser.get().getRole(), "ROLE_NAGARSEVAK"))
                {
                    Nagarsevak nagarsevak=nagarsevakRepository.findNagarsevakByEmail(gotuser.get().getEmail());
                    userid=nagarsevak.getNagarsevak_id();
                    name=nagarsevak.getFirstname()+ " "+nagarsevak.getLastname();
                }
                else if(Objects.equals(gotuser.get().getRole(), "ROLE_ADMIN"))
                {
                    userid=0;
                    name="Admin";
                }
                else {
                    throw new APIRequestException("Wrong Role!");
                }
                LoginResponseDTO loginRespDTO=LoginResponseDTO.builder()
                        .email(gotuser.get().getEmail())
                        .user_id(userid)
                        .name(name)
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

    @Override
    public Boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Boolean status=false;
        Optional<Users> user=userRepository.findById(changePasswordDTO.getEmail());
        if(user.isEmpty())
            throw new APIRequestException("User with given email not found");
        if(Objects.equals(changePasswordDTO.getNewPassword(), changePasswordDTO.getConfirmPassword()))
        {
            if(passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.get().getPassword()))
            {
                user.get().setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(user.get());
                status=true;
            }
            else
               throw new APIRequestException("Wrong old password");
        }
        else
            throw new APIRequestException("New password and confirm password not matching ");
        return status;
    }
}
