package com.user.service.Implementation;

import com.user.service.DTOs.ApiResponseDTO;
import com.user.service.DTOs.UserRequest;
import com.user.service.Entities.Area;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Repositories.AreaRepository;
import com.user.service.Repositories.NagarsevakRepository;
import com.user.service.Repositories.UserRepository;
import com.user.service.Services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

   private NagarsevakRepository nagarsevakRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
   private UserRepository userRepository;
   private AreaRepository areaRepository;
    @Autowired
    private EmailService emailService;
    @Override
    @Transactional
    public Nagarsevak registerNagarsevak(UserRequest gotnagarsevak)
    {

        Optional<Area> area=areaRepository.findById(gotnagarsevak.getArea_code());
        if(area.isPresent()) {
            //Add user to user database
            Users user = new Users();
            String password = PasswordGeneratorService.generatePassword();
            user.setEmail(gotnagarsevak.getEmail());
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("ROLE_NAGARSEVAK");
            Users newuser = userRepository.save(user);

            System.out.println("email"+user.getEmail());
            System.out.println("pass"+user.getPassword());
            Nagarsevak nagarsevak= Nagarsevak.builder()
                    .mobile_no(gotnagarsevak.getMobile_no())
                    .firstname(gotnagarsevak.getFirstname())
                    .lastname(gotnagarsevak.getLastname())
                    .area(area.get())
                    .user(newuser)
                    .build();
           Nagarsevak savedNagarsevak = nagarsevakRepository.save(nagarsevak);

            //for sending email
            String subject = "Login Credentials for CitizenConnect";
            String msg = "Hello " + savedNagarsevak.getFirstname() + " " + savedNagarsevak.getLastname() + "\nYou are assigned as Nagarsevak for area " + savedNagarsevak.getArea().getName() + "\nPlease login in CitizenConnect app with following credentials. " + "\nUsername = " + savedNagarsevak.getUser().getEmail() + "\nPassword = " + password + "\nPlease change password after login.";
            String to = savedNagarsevak.getUser().getEmail();
            if (emailService.sendEmail(subject, msg, to)) {
                System.out.println("mail success");
            } else {
                System.out.println("mail failed");
                throw new APIRequestException("Sending mail failed");
            }
            return savedNagarsevak;

        }
        else
        {
            throw new APIRequestException("Area not found");
        }

    }
}
