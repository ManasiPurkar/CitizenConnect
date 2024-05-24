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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class CitizenServiceImpl implements CitizenService{

    private static final Logger logger = LoggerFactory.getLogger(CitizenServiceImpl.class);

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
        logger.info("Registering new citizen with email: {}", gotcitizen.getEmail());

        Optional<Area> area=areaRepository.findById(gotcitizen.getAreaCode());
        logger.debug("Area got: {}", gotcitizen.getAreaCode());

        if(area.isPresent()) {
            logger.debug("Area present with code: {}", gotcitizen.getAreaCode());

            //Add user to user database
            Users user = new Users();
            String password = PasswordGeneratorService.generatePassword();
            user.setEmail(gotcitizen.getEmail());
            user.setPassword(passwordEncoder.encode(password));

            user.setRole("ROLE_CITIZEN");
            Users newuser = userRepository.save(user);

            logger.debug("New user created with email: {}", user.getEmail());

            Citizen citizen= Citizen.builder()
                    .mobile_no(gotcitizen.getMobile_no())
                    .firstname(gotcitizen.getFirstname())
                    .lastname(gotcitizen.getLastname())
                    .area(area.get())
                    .user(newuser)
                    .active(true)
                    .build();
            Citizen savedCitizen = citizenRepository.save(citizen);
            System.out.println("saved citizen"+savedCitizen);
            logger.info("Citizen {} {} registered for area: {}", savedCitizen.getFirstname(), savedCitizen.getLastname(), savedCitizen.getArea().getName());

            //for sending email
            String subject = "Login Credentials for CitizenConnect";
            String msg = "Hello " + savedCitizen.getFirstname() + " " + savedCitizen.getLastname() + "\nYou are registered as citizen for area " + savedCitizen.getArea().getName() + "\nPlease login in CitizenConnect app with following credentials. " + "\nUsername = " + savedCitizen.getUser().getEmail() + "\nPassword = " + password + "\nPlease change password after login.";
            String to = savedCitizen.getUser().getEmail();
            if (emailService.sendEmail(subject, msg, to)) {
                logger.info("Email sent successfully to: {}", to);

            } else {
                logger.error("Failed to send email to: {}", to);

                throw new APIRequestException("Sending mail failed");
            }
            return savedCitizen;

        }
        else
        {
            logger.error("Area not found for area code: {}", gotcitizen.getAreaCode());

            throw new APIRequestException("Area not found");
        }

    }

    /*
    @Override
    public CitizenDTO getCitizen(int citizenId)
    {
        Optional<Citizen> citizen=citizenRepository.findById(citizenId);
        if(citizen.isPresent()) {
            return CitizenDTO.builder()
                    .name(citizen.get().getFirstname() + " " + citizen.get().getLastname())
                    .mobile_no(citizen.get().getMobile_no())
                    .email(citizen.get().getUser().getEmail())
                    .areaCode(citizen.get().getArea().getAreaCode())
                    .area_name(citizen.get().getArea().getName())
                    .build();
        }
        else
        {
            throw new APIRequestException("citizen with given id not found");
        }
    }
    */

}
