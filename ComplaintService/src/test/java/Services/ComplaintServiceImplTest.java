package Services;

import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Entities.Departments;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Repositories.ComplaintsRepository;
import com.complaint.service.Repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.complaint.service.ServiceImpls.ComplaintServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ComplaintServiceImplTest {

    @Mock
    private ComplaintsRepository complaintsRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ComplaintServiceImpl complaintService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComplaint_Success() {
        // Arrange
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .address("123 Main St")
                .title("Test Title")
                .description("Test Description")
                .citizenId(1)
                .areaCode("12345")
                .areaName("Test Area")
                .department_code(1)
                .build();

        Departments department = new Departments();
        department.setDepartment_id(1);

        Complaints complaint = Complaints.builder()
                .address(complaintDTO.getAddress())
                .date(Date.valueOf(LocalDate.now()))
                .title(complaintDTO.getTitle())
                .status("pending")
                .department(department)
                .description(complaintDTO.getDescription())
                .citizenId(complaintDTO.getCitizenId())
                .eventTime(LocalTime.now())
                .areaCode(complaintDTO.getAreaCode())
                .areaName(complaintDTO.getAreaName())
                .build();

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(complaintsRepository.save(any(Complaints.class))).thenReturn(complaint);

        // Act
        Complaints createdComplaint = complaintService.createComplaint(complaintDTO);

        // Assert
        assertNotNull(createdComplaint);
        assertEquals(complaintDTO.getTitle(), createdComplaint.getTitle());
        assertEquals(complaintDTO.getDescription(), createdComplaint.getDescription());
        assertEquals(department, createdComplaint.getDepartment());

        verify(departmentRepository, times(1)).findById(1);
        verify(complaintsRepository, times(1)).save(any(Complaints.class));
        System.out.println("Create complaint success tested");
    }

    @Test
    public void testCreateComplaint_ThrowsAPIRequestException() {
        // Arrange
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .address("123 Main St")
                .title("Test Title")
                .description("Test Description")
                .citizenId(1)
                .areaCode("12345")
                .areaName("Test Area")
                .department_code(1)
                .build();

        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        APIRequestException exception = assertThrows(APIRequestException.class, () -> {
            complaintService.createComplaint(complaintDTO);
        });

        assertEquals("Wrong Department !", exception.getMessage());

        verify(departmentRepository, times(1)).findById(1);
        verify(complaintsRepository, never()).save(any(Complaints.class));
        System.out.println("create complaint failure tested");
    }

    @Test
    public void testGetComplaint_Success() {
        // Arrange
        Complaints complaint = new Complaints();
        complaint.setComplaint_id(1);

        when(complaintsRepository.findById(1)).thenReturn(Optional.of(complaint));

        // Act
        Complaints foundComplaint = complaintService.getComplaint(1);

        // Assert
        assertNotNull(foundComplaint);
        assertEquals(1, foundComplaint.getComplaint_id());

        verify(complaintsRepository, times(1)).findById(1);
        System.out.println("get complaint success tested");
    }

    @Test
    public void testGetComplaint_ThrowsAPIRequestException() {
        // Arrange
        when(complaintsRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            complaintService.getComplaint(1);
        });

        verify(complaintsRepository, times(1)).findById(1);
        System.out.println("get complaint failure tested");
    }

    @Test
    public void testGetCitizenComplaints() {
        // Arrange
        Complaints complaint1 = new Complaints();
        complaint1.setComplaint_id(1);
        Complaints complaint2 = new Complaints();
        complaint2.setComplaint_id(2);

        when(complaintsRepository.findByCitizenId(1)).thenReturn(Arrays.asList(complaint1, complaint2));

        // Act
        List<Complaints> complaints = complaintService.getCitizenComplaints(1);

        // Assert
        assertNotNull(complaints);
        assertEquals(2, complaints.size());

        verify(complaintsRepository, times(1)).findByCitizenId(1);
        System.out.println("get citizen complaint success tested");
    }

    @Test
    public void testGetAreaComplaints() {
        // Arrange
        Complaints complaint1 = new Complaints();
        complaint1.setComplaint_id(1);
        Complaints complaint2 = new Complaints();
        complaint2.setComplaint_id(2);

        when(complaintsRepository.findByAreaCode("12345")).thenReturn(Arrays.asList(complaint1, complaint2));

        // Act
        List<Complaints> complaints = complaintService.getAreaComplaints("12345");

        // Assert
        assertNotNull(complaints);
        assertEquals(2, complaints.size());

        verify(complaintsRepository, times(1)).findByAreaCode("12345");
        System.out.println("get area complaint success tested");
    }

    @Test
    public void testChangeComplStatus_Success() {
        // Arrange
        Complaints complaint = new Complaints();
        complaint.setComplaint_id(1);
        complaint.setStatus("pending");

        when(complaintsRepository.findById(1)).thenReturn(Optional.of(complaint));
        when(complaintsRepository.save(any(Complaints.class))).thenReturn(complaint);

        // Act
        Complaints updatedComplaint = complaintService.changeComplStatus(1, "solved");

        // Assert
        assertNotNull(updatedComplaint);
        assertEquals("solved", updatedComplaint.getStatus());

        verify(complaintsRepository, times(1)).findById(1);
        verify(complaintsRepository, times(1)).save(any(Complaints.class));
        System.out.println("successful compl status change tested");
    }

    @Test
    public void testChangeComplStatus_ThrowsAPIRequestException_InvalidStatus() {
        // Act & Assert
        APIRequestException exception = assertThrows(APIRequestException.class, () -> {
            complaintService.changeComplStatus(1, "invalidStatus");
        });

        assertEquals("status is wrong", exception.getMessage());
        System.out.println("unsuccessful compl status change tested with wrong status");
    }

    @Test
    public void testChangeComplStatus_ThrowsAPIRequestException_ComplaintNotFound() {
        // Arrange
        when(complaintsRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        APIRequestException exception = assertThrows(APIRequestException.class, () -> {
            complaintService.changeComplStatus(1, "solved");
        });

        assertEquals("Complaint with given id not found", exception.getMessage());

        verify(complaintsRepository, times(1)).findById(1);
        System.out.println("unsuccessful compl status change tested with wrong complaint id");
    }
}
