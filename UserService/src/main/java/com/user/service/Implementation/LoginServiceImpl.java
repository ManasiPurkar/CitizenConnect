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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

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
        logger.info("Attempting to log in user with email: {}", user.getEmail());

        Boolean status=false;
        System.out.println("email got "+user.getEmail());
        Optional<Users> gotuser = userRepository.findById(user.getEmail());

        if (gotuser.isPresent()) {
            logger.debug("User found with email: {}", user.getEmail());

            if (passwordEncoder.matches(user.getPassword(),gotuser.get().getPassword())) {
                status=true;
                int userid;
                String name;
                logger.debug("User role: {}", gotuser.get().getRole());

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
                    logger.error("Wrong role for user with email: {}", user.getEmail());

                    throw new APIRequestException("Wrong Role!");
                }
                LoginResponseDTO loginRespDTO=LoginResponseDTO.builder()
                        .email(gotuser.get().getEmail())
                        .user_id(userid)
                        .name(name)
                        .role(gotuser.get().getRole())
                        .build();
                logger.info("Login successful for user with email: {}", user.getEmail());

                return Pair.of(status,loginRespDTO); // Login successful
            } else {
                logger.error("Incorrect password for user with email: {}", user.getEmail());

                throw new APIRequestException("Incorrect Username or Password");
            }
        }
        else {
            logger.error("User not found with email: {}", user.getEmail());

            throw new APIRequestException("User not found");
        }
    }

    @Override
    public Boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        logger.info("Attempting to change password for user with email: {}", changePasswordDTO.getEmail());

        Boolean status=false;
        Optional<Users> user=userRepository.findById(changePasswordDTO.getEmail());
        if(user.isEmpty()) {
            logger.error("User not found with email: {}", changePasswordDTO.getEmail());

            throw new APIRequestException("User with given email not found");
        }
        if(Objects.equals(changePasswordDTO.getNewPassword(), changePasswordDTO.getConfirmPassword()))
        {
            if(passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.get().getPassword()))
            {
                user.get().setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(user.get());
                status=true;
                logger.info("Password changed successfully for user with email: {}", changePasswordDTO.getEmail());

            }
            else {
                logger.error("Wrong old password for user with email: {}", changePasswordDTO.getEmail());

                throw new APIRequestException("Wrong old password");
            }
        }
        else {
            logger.error("New password and confirm password do not match for user with email: {}", changePasswordDTO.getEmail());

            throw new APIRequestException("New password and confirm password not matching ");
        }
        return status;
    }
}
