package com.user.service.Controllers;

import com.user.service.DTOs.CitizenDTO;
import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Complaints;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Services.CitizenService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen")
@CrossOrigin(origins = "*")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;
    @Autowired
    private ComplaintService complaintService;
    @GetMapping("/{citizenId}")
    public ResponseEntity<CitizenDTO> getCitizen(@PathVariable int citizenId) {
        try {
            CitizenDTO citizenDTO=citizenService.getCitizen(citizenId);
            List<Complaints> complaints=complaintService.getCitizenComplaints(citizenId);
            citizenDTO.setComplaintsList(complaints);
            return ResponseEntity.ok(citizenDTO);
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while getting citizen", ex.getMessage());
        }
    }

    @Validated
    @PostMapping("/register-complaint")
    public ResponseEntity<Complaints> registerComplaint(@Valid @RequestBody Complaints complaint)
    {
        return complaintService.registerComplaint(complaint);
    }

}
