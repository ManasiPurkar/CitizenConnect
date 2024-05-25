package com.user.service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.Controllers.CitizenController;
import com.user.service.DTOs.ComplaintDTO;
import com.user.service.Entities.Area;
import com.user.service.Entities.Citizen;
import com.user.service.Entities.Complaints;
import com.user.service.Exceptions.APIRequestException;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Repositories.AreaRepository;
import com.user.service.Repositories.CitizenRepository;
import com.user.service.Services.CitizenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CitizenController.class)
public class CitizenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitizenService citizenService;

    @MockBean
    private ComplaintService complaintService;

    @MockBean
    private AreaRepository areaRepository;

    @MockBean
    private CitizenRepository citizenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(CitizenControllerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerComplaint_Success() throws Exception {
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .areaCode("AREA123")
                .description("Test complaint")
                .build();

        Area area = new Area();
        area.setAreaCode("AREA123");
        area.setName("Test Area");

        Complaints complaint = new Complaints();
        complaint.setDescription("Test complaint");

        when(areaRepository.findById("AREA123")).thenReturn(Optional.of(area));
        when(complaintService.registerComplaint(any(ComplaintDTO.class))).thenReturn(ResponseEntity.ok(complaint));

        mockMvc.perform(post("/citizen/register-complaint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test complaint"));

        logger.info("registerComplaint_Success test passed");
    }

    @Test
    void registerComplaint_AreaNotFound() throws Exception {
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .areaCode("AREA123")
                .description("Test complaint")
                .build();

        when(areaRepository.findById("AREA123")).thenReturn(Optional.empty());

        mockMvc.perform(post("/citizen/register-complaint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Wrong area"));

        logger.info("registerComplaint_AreaNotFound test passed");
    }

    @Test
    void getCitizenAreaCompl_Success() throws Exception {
        int citizenId = 1;
        Citizen citizen = new Citizen();
        Area area = new Area();
        area.setAreaCode("AREA123");
        area.setName("Test Area");
        citizen.setArea(area);

        Complaints complaint1 = new Complaints();
        complaint1.setDescription("Complaint 1 description");
        complaint1.setAreaCode("AREA123");

        Complaints complaint2 = new Complaints();
        complaint2.setDescription("Complaint 2 description");
        complaint2.setAreaCode("AREA123");

        List<Complaints> complaintsList = List.of(complaint1, complaint2);


        when(citizenRepository.findById(citizenId)).thenReturn(Optional.of(citizen));
        when(complaintService.getAreaComplaints("AREA123")).thenReturn(ResponseEntity.ok(complaintsList));

        mockMvc.perform(get("/citizen/area-complaints/{citizenId}", citizenId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty());

        logger.info("getCitizenAreaCompl_Success test passed");
    }

    @Test
    void getCitizenAreaCompl_CitizenNotFound() throws Exception {
        int citizenId = 1;

        when(citizenRepository.findById(citizenId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/citizen/area-complaints/{citizenId}", citizenId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Citizen with given Id not found"));

        logger.info("getCitizenAreaCompl_CitizenNotFound test passed");
    }
}
