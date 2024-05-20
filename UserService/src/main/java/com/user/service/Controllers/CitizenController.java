package com.user.service.Controllers;

import com.user.service.DTOs.CitizenDTO;
import com.user.service.DTOs.ComplaintDTO;
import com.user.service.DTOs.LoginDTO;
import com.user.service.DTOs.LoginResponseDTO;
import com.user.service.Entities.Area;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Complaints;
import com.user.service.Entities.Nagarsevak;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Repositories.AreaRepository;
import com.user.service.Repositories.CitizenRepository;
import com.user.service.Services.CitizenService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/citizen")
//@CrossOrigin(origins = "*")
public class CitizenController {
    private static final Logger logger = LoggerFactory.getLogger(CitizenController.class);

    @Autowired
    private CitizenService citizenService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private CitizenRepository citizenRepository;

    /*
    @GetMapping("/{citizenId}")
    public ResponseEntity<CitizenDTO> getCitizen(@PathVariable int citizenId) {
        try {
            CitizenDTO citizenDTO=citizenService.getCitizen(citizenId);
            ResponseEntity<List<Complaints>> complaints=complaintService.getCitizenComplaints(citizenId);
            citizenDTO.setComplaintsList(complaints.getBody());
            return ResponseEntity.ok(citizenDTO);
        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while getting citizen", ex.getMessage());
        }
    }
*/
    @Validated
    @PostMapping("/register-complaint")
    public ResponseEntity<Complaints> registerComplaint(@Valid @RequestBody ComplaintDTO complaint)
    {
        logger.info("Registering complaint for area code: {}", complaint.getAreaCode());

        Optional<Area> area=areaRepository.findById(complaint.getAreaCode());
        if(area.isEmpty()) {
            logger.error("Area not found for area code: {}", complaint.getAreaCode());

            throw new APIRequestException("Wrong area");
        }
        complaint.setAreaName(area.get().getName());
        ResponseEntity<Complaints> result= complaintService.registerComplaint(complaint);

        logger.info("Complaint registered successfully for area code: {}", complaint.getAreaCode());

        return result;
    }

    @GetMapping("/area-complaints/{citizenId}")
    public ResponseEntity<List<Complaints>> getCitizenAreaCompl(@PathVariable int citizenId) {
        logger.info("Fetching complaints for citizen ID: {}", citizenId);

        try {
            Optional<Citizen> citizen=citizenRepository.findById(citizenId);
            if(citizen.isEmpty())
            {
                logger.error("Citizen not found with ID: {}", citizenId);

                throw new APIRequestException("Citizen with given Id not found");
            }
            String areaCode=citizen.get().getArea().getAreaCode();
            logger.info("Fetched complaints for area code: {}", areaCode);

            return complaintService.getAreaComplaints(areaCode);

        } catch (Exception ex) {
            logger.error("Error while fetching complaints for citizen ID: {}", citizenId, ex);

            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while getting citizen's area complaints", ex.getMessage());
        }
    }
}
