package com.user.service.Controller;

import com.user.service.Entities.Area;
import com.user.service.Entities.Complaints;
import com.user.service.Entities.Nagarsevak;
import com.user.service.ExternalServices.ComplaintService;
import com.user.service.Repositories.NagarsevakRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.user.service.Controllers.NagarsevakController;

@ExtendWith(MockitoExtension.class)
public class NagarsevakControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NagarsevakRepository nagarsevakRepository;

    @Mock
    private ComplaintService complaintService;

    @InjectMocks
    private NagarsevakController nagarsevakController;

    @Test
    void getNagarsevakCompl_Success() throws Exception {
        int nagarsevakId = 1;

        Area area = new Area();
        area.setAreaCode("AREA123");
        area.setName("Test Area");

        Nagarsevak nagarsevak = new Nagarsevak();
        nagarsevak.setArea(area);

        Complaints complaint1 = new Complaints();
        complaint1.setDescription("Complaint 1 description");
        complaint1.setAreaCode("AREA123");

        Complaints complaint2 = new Complaints();
        complaint2.setDescription("Complaint 2 description");
        complaint2.setAreaCode("AREA123");

        List<Complaints> complaintsList = List.of(complaint1, complaint2);

        when(nagarsevakRepository.findById(nagarsevakId)).thenReturn(Optional.of(nagarsevak));
        when(complaintService.getAreaComplaints("AREA123")).thenReturn(ResponseEntity.ok(complaintsList));

        mockMvc = MockMvcBuilders.standaloneSetup(nagarsevakController).build();

        mockMvc.perform(get("/nagarsevak/complaints/{nagarsevakId}", nagarsevakId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Complaint 1 description"))
                .andExpect(jsonPath("$[1].description").value("Complaint 2 description"));
    }


}

