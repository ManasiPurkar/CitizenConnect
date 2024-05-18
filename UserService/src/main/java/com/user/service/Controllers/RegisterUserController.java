package com.user.service.Controllers;


import com.user.service.DTOs.UserRequest;
import com.user.service.DTOs.UserResponse;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.Exceptions.GlobalExceptionhandler;
import com.user.service.Implementation.EmailService;
import com.user.service.Services.AdminService;
import com.user.service.Services.CitizenService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
//@CrossOrigin(origins = "*")
public class RegisterUserController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private CitizenService citizenService;

    @Validated
    @PostMapping("/Nagarsevak")
    public ResponseEntity<UserResponse> addNagarsevak(@Valid @RequestBody UserRequest nagarsevak) {

        try {

            Nagarsevak addedNagarsevak = adminService.registerNagarsevak(nagarsevak);

            UserResponse userResponse=UserResponse.builder()
                    .email(addedNagarsevak.getUser().getEmail())
                    .role(addedNagarsevak.getUser().getRole())
                    .areaCode(addedNagarsevak.getArea().getAreaCode())
                    .area_name(addedNagarsevak.getArea().getName())
                    .firstname(addedNagarsevak.getFirstname())
                    .lastname(addedNagarsevak.getLastname())
                    .mobile_no(addedNagarsevak.getMobile_no())
                    .build();


            return ResponseEntity.ok(userResponse);

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = ex.getCause().getMessage();
            String duplicateEntryMessage = null;

            if (errorMessage.contains("Duplicate entry")) {
                // Extract the part of the message that contains the duplicate entry information
                duplicateEntryMessage = errorMessage.substring(errorMessage.indexOf("Duplicate entry"), errorMessage.indexOf("for key"));
            }

            if (duplicateEntryMessage != null) {
                throw new APIRequestException(duplicateEntryMessage, ex.getMessage());
            } else {
                // If the message doesn't contain the expected format, throw a generic exception
                throw new APIRequestException("Duplicate entry constraint violation occurred", ex.getMessage());
            }
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while adding the Nagarsevak.", ex.getMessage());
        }
    }



    @Validated
    @PostMapping("/Citizen")
    public ResponseEntity<UserResponse> addCitizen(@Valid @RequestBody UserRequest citizen) {

        try {

            Citizen addedCitizen = citizenService.registerCitizen(citizen);

              UserResponse userResponse=UserResponse.builder()
                      .email(addedCitizen.getUser().getEmail())
                      .role(addedCitizen.getUser().getRole())
                      .areaCode(addedCitizen.getArea().getAreaCode())
                      .area_name(addedCitizen.getArea().getName())
                      .firstname(addedCitizen.getFirstname())
                      .lastname(addedCitizen.getLastname())
                      .mobile_no(addedCitizen.getMobile_no())
                      .build();


            return ResponseEntity.ok(userResponse);

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = ex.getCause().getMessage();
            String duplicateEntryMessage = null;

            if (errorMessage.contains("Duplicate entry")) {
                // Extract the part of the message that contains the duplicate entry information
                duplicateEntryMessage = errorMessage.substring(errorMessage.indexOf("Duplicate entry"), errorMessage.indexOf("for key"));
            }

            if (duplicateEntryMessage != null) {
                throw new APIRequestException(duplicateEntryMessage, ex.getMessage());
            } else {
                // If the message doesn't contain the expected format, throw a generic exception
                throw new APIRequestException("Duplicate entry constraint violation occurred", ex.getMessage());
            }
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while adding the Citizen.", ex.getMessage());
        }
    }



}
