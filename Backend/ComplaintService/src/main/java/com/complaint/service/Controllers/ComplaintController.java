package com.complaint.service.Controllers;

import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Services.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;
    @Validated
    @PostMapping("complaint")
    public ResponseEntity<ComplaintDTO> registerComplaint(@Valid @RequestBody Complaints complaint) {
        try {

            Complaints regcomplaint=complaintService.createComplaint(complaint);
            ComplaintDTO complaintDTO=ComplaintDTO.builder()
                    .complaint_id(regcomplaint.getComplaint_id())
                    .address(regcomplaint.getAddress())
                    .status(regcomplaint.getStatus())
                    .title(regcomplaint.getTitle())
                    .department(regcomplaint.getDepartment())
                    .date(regcomplaint.getDate())
                    .No_Of_Votes(regcomplaint.getNo_Of_Votes())
                    .build();
            return ResponseEntity.ok(complaintDTO);

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
                throw new APIRequestException("Error while registering complaint.", ex.getMessage());
        }
    }
    @GetMapping("complaint")
    public ResponseEntity<List<ComplaintDTO>> getallComplaints() {
        try {

            List<Complaints> gotcomplaints=complaintService.getallcomplaints();
            List<ComplaintDTO> complaintDTOList=new ArrayList<>();
            for(Complaints regcomplaint:gotcomplaints) {
                ComplaintDTO complaintDTO = ComplaintDTO.builder()
                        .complaint_id(regcomplaint.getComplaint_id())
                        .address(regcomplaint.getAddress())
                        .status(regcomplaint.getStatus())
                        .title(regcomplaint.getTitle())
                        .department(regcomplaint.getDepartment())
                        .date(regcomplaint.getDate())
                        .No_Of_Votes(regcomplaint.getNo_Of_Votes())
                        .build();
                complaintDTOList.add(complaintDTO);
            }
            return ResponseEntity.ok(complaintDTOList);

        }  catch (Exception ex) {
            if (ex instanceof APIRequestException) {
                throw new APIRequestException(ex.getMessage());
            } else
                throw new APIRequestException("Error while registering complaint.", ex.getMessage());
        }
    }
}
