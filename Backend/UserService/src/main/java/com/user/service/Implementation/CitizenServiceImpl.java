package com.user.service.Implementation;

import com.user.service.DTOs.CitizenDTO;
import com.user.service.DTOs.UserRequest;
import com.user.service.Entities.Area;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Entities.Users;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Repositories.AreaRepository;
import com.user.service.Repositories.CitizenRepository;
import com.user.service.Repositories.NagarsevakRepository;
import com.user.service.Repositories.UserRepository;
import com.user.service.Services.CitizenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CitizenServiceImpl implements CitizenService{

    private CitizenRepository citizenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AreaRepository areaRepository;
    @Autowired
    private EmailService emailService;
    @Override
    @Transactional
    public Citizen registerCitizen(UserRequest gotcitizen)
    {

        Optional<Area> area=areaRepository.findById(gotcitizen.getArea_code());
        if(area.isPresent()) {
            //Add user to user database
            Users user = new Users();
            String password = PasswordGeneratorService.generatePassword();
            user.setEmail(gotcitizen.getEmail());
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("ROLE_CITIZEN");
            Users newuser = userRepository.save(user);

            System.out.println("email"+user.getEmail());
            System.out.println("pass"+user.getPassword());
            Citizen citizen= Citizen.builder()
                    .mobile_no(gotcitizen.getMobile_no())
                    .firstname(gotcitizen.getFirstname())
                    .lastname(gotcitizen.getLastname())
                    .area(area.get())
                    .user(newuser)
                    .active(true)
                    .build();
            Citizen savedCitizen = citizenRepository.save(citizen);

            //for sending email
            String subject = "Login Credentials for CitizenConnect";
            String msg = "Hello " + savedCitizen.getFirstname() + " " + savedCitizen.getLastname() + "\nYou are registered as citizen for area " + savedCitizen.getArea().getName() + "\nPlease login in CitizenConnect app with following credentials. " + "\nUsername = " + savedCitizen.getUser().getEmail() + "\nPassword = " + password + "\nPlease change password after login.";
            String to = savedCitizen.getUser().getEmail();
            if (emailService.sendEmail(subject, msg, to)) {
                System.out.println("mail success");
            } else {
                System.out.println("mail failed");
                throw new APIRequestException("Sending mail failed");
            }
            return savedCitizen;

        }
        else
        {
            throw new APIRequestException("Area not found");
        }

    }

    @Override
    public CitizenDTO getCitizen(int citizenId)
    {
        Optional<Citizen> citizen=citizenRepository.findById(citizenId);
        if(citizen.isPresent()) {
            return CitizenDTO.builder()
                    .name(citizen.get().getFirstname() + " " + citizen.get().getLastname())
                    .mobile_no(citizen.get().getMobile_no())
                    .email(citizen.get().getUser().getEmail())
                    .area_code(citizen.get().getArea().getArea_code())
                    .area_name(citizen.get().getArea().getName())
                    .build();
        }
        else
        {
            throw new APIRequestException("citizen with given id not found");
        }
    }
}
