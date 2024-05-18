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

@RestController
@RequestMapping("/nagarsevak")
//@CrossOrigin(origins = "*")
public class NagarsevakController {

    @Autowired
    private NagarsevakRepository nagarsevakRepository;
    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/complaints/{nagarsevakId}")
    public ResponseEntity<List<Complaints>> getNagarsevakCompl(@PathVariable int nagarsevakId) {
        try {
            Optional<Nagarsevak> nagarsevak=nagarsevakRepository.findById(nagarsevakId);
            if(nagarsevak.isEmpty())
                throw new APIRequestException("Nagarsevak with given Id not found");
            String areaCode=nagarsevak.get().getArea().getAreaCode();
            return complaintService.getAreaComplaints(areaCode);

        } catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while getting citizen", ex.getMessage());
        }
    }
}
