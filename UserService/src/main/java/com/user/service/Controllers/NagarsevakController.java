package com.user.service.Controllers;

import com.user.service.Entities.Complaints;
import com.user.service.Entities.Nagarsevak;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Repositories.NagarsevakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.user.service.Exceptions.APIRequestException;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/nagarsevak")
//@CrossOrigin(origins = "*")
public class NagarsevakController {

    private static final Logger logger = LoggerFactory.getLogger(NagarsevakController.class);

    @Autowired
    private NagarsevakRepository nagarsevakRepository;
    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/complaints/{nagarsevakId}")
    public ResponseEntity<List<Complaints>> getNagarsevakCompl(@PathVariable int nagarsevakId) {
        try {
            Optional<Nagarsevak> nagarsevak=nagarsevakRepository.findById(nagarsevakId);
            if(nagarsevak.isEmpty()) {
                logger.error("Nagarsevak with ID: {} not found", nagarsevakId);
                throw new APIRequestException("Nagarsevak with given Id not found");
            }
            String areaCode=nagarsevak.get().getArea().getAreaCode();
            logger.debug("Nagarsevak found with area code: {}", areaCode);
            logger.info("Successfully retrieved complaints for area code: {}", areaCode);
            return complaintService.getAreaComplaints(areaCode);

        } catch (Exception ex) {
            logger.error("APIRequestException occurred: {}", ex.getMessage());

            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while getting citizen", ex.getMessage());
        }
    }
}
