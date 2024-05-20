package Controllers;

import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Services.ComplaintService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.complaint.service.Controllers.ComplaintController;
import com.complaint.service.Exceptions.ApiExceptionHandler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ComplaintControllerTest {


    @Mock
    private ComplaintService complaintService;

    @InjectMocks
    private ComplaintController complaintController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(complaintController)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void testRegisterComplaint() throws Exception {
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .title("Test Title")
                .description("Test Description")
                .address("Test Address")
                .department_code(1)
                .citizenId(1)
                .areaCode("123")
                .areaName("Test Area")
                .build();

        Complaints complaint = Complaints.builder()
                .complaint_id(1)
                .title("Test Title")
                .description("Test Description")
                .address("Test Address")
                .date(Date.valueOf(LocalDate.now()))
                .status("pending")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("123")
                .areaName("Test Area")
                .build();

        when(complaintService.createComplaint(any(ComplaintDTO.class))).thenReturn(complaint);

        mockMvc.perform(post("/complaint/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Title\",\"description\":\"Test Description\",\"address\":\"Test Address\",\"department_code\":1,\"citizenId\":1,\"areaCode\":\"123\",\"areaName\":\"Test Area\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCitizenComplaints() throws Exception {
        Complaints complaint1 = Complaints.builder()
                .complaint_id(1)
                .title("Test Title 1")
                .description("Test Description 1")
                .address("Test Address 1")
                .date(Date.valueOf(LocalDate.now()))
                .status("pending")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("123")
                .areaName("Test Area 1")
                .build();

        Complaints complaint2 = Complaints.builder()
                .complaint_id(2)
                .title("Test Title 2")
                .description("Test Description 2")
                .address("Test Address 2")
                .date(Date.valueOf(LocalDate.now()))
                .status("ongoing")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("124")
                .areaName("Test Area 2")
                .build();

        List<Complaints> complaintsList = Arrays.asList(complaint1, complaint2);

        when(complaintService.getCitizenComplaints(anyInt())).thenReturn(complaintsList);

        mockMvc.perform(get("/complaint/citizen/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetComplaint_Success() throws Exception {
        Complaints complaint = Complaints.builder()
                .complaint_id(1)
                .title("Test Title")
                .description("Test Description")
                .address("Test Address")
                .date(Date.valueOf(LocalDate.now()))
                .status("pending")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("123")
                .areaName("Test Area")
                .build();

        when(complaintService.getComplaint(anyInt())).thenReturn(complaint);

        mockMvc.perform(get("/complaint/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.complaint_id").value(1))
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void testGetAreaComplaints_Success() throws Exception {
        Complaints complaint1 = Complaints.builder()
                .complaint_id(1)
                .title("Test Title 1")
                .description("Test Description 1")
                .address("Test Address 1")
                .date(Date.valueOf(LocalDate.now()))
                .status("pending")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("123")
                .areaName("Test Area 1")
                .build();

        Complaints complaint2 = Complaints.builder()
                .complaint_id(2)
                .title("Test Title 2")
                .description("Test Description 2")
                .address("Test Address 2")
                .date(Date.valueOf(LocalDate.now()))
                .status("ongoing")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("124")
                .areaName("Test Area 2")
                .build();

        List<Complaints> complaintsList = Arrays.asList(complaint1, complaint2);

        when(complaintService.getAreaComplaints(anyString())).thenReturn(complaintsList);

        mockMvc.perform(get("/complaint/area/123"))
                .andExpect(status().isOk());
    }

    @Test
    void testChangeComplStatus_Success() throws Exception {
        Complaints complaint = Complaints.builder()
                .complaint_id(1)
                .title("Test Title")
                .description("Test Description")
                .address("Test Address")
                .date(Date.valueOf(LocalDate.now()))
                .status("ongoing")
                .eventTime(LocalTime.now())
                .citizenId(1)
                .areaCode("123")
                .areaName("Test Area")
                .build();

        when(complaintService.changeComplStatus(anyInt(), anyString())).thenReturn(complaint);

        mockMvc.perform(put("/complaint/change-status/1/ongoing"))
                .andExpect(status().isOk());
    }

    @Test
    void testChangeComplStatus_APIRequestException() throws Exception {
        APIRequestException exception = new APIRequestException("Status is invalid");

        when(complaintService.changeComplStatus(anyInt(), anyString())).thenThrow(exception);

        mockMvc.perform(put("/complaint/change-status/1/invalidStatus"))
                .andExpect(status().isBadRequest());
    }
}