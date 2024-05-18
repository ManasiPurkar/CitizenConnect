package com.complaint.service.ServiceImpls;

import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Complaints;
import com.complaint.service.Entities.Departments;
import com.complaint.service.Exceptions.APIRequestException;
import com.complaint.service.Repositories.ComplaintsRepository;
import com.complaint.service.Repositories.DepartmentRepository;
import com.complaint.service.Services.ComplaintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableTransactionManagement
public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintsRepository complaintsRepository;
    private DepartmentRepository departmentRepository;
    @Override
    public Complaints createComplaint(ComplaintDTO complaint)
    {

        System.out.println("department code"+complaint.getDepartment_code());
        Optional<Departments> department=departmentRepository.findById(complaint.getDepartment_code());
        System.out.println("department"+department);
        if(department.isEmpty())
            throw new APIRequestException("Wrong Department !");
        Complaints regcomplaint=Complaints.builder()
                .address(complaint.getAddress())
                .date(Date.valueOf(LocalDate.now()))
                .title(complaint.getTitle())
//                .No_Of_Votes(1)
                .status("pending")
                .department(department.get())
                .description(complaint.getDescription())
                .citizenId(complaint.getCitizenId())
                .eventTime(LocalTime.now())
                .areaCode(complaint.getAreaCode())
                .areaName(complaint.getAreaName())
                .build();
        System.out.println("regcomp"+regcomplaint);
        return complaintsRepository.save(regcomplaint);
    }

    /*
    @Override
    public List<Complaints> getallcomplaints()
    {
        return complaintsRepository.findAll();
    }
    */
    @Override
    public Complaints getComplaint(int complid)
    {
        Optional<Complaints> complaint= complaintsRepository.findById(complid);
        return complaint.get();
    }

    @Override
    public List<Complaints> getCitizenComplaints(int citizenId)
    {
       return complaintsRepository.findByCitizenId(citizenId);

    }

    @Override
    public  List<Complaints> getAreaComplaints(String areaCode)
    {

        return complaintsRepository.findByAreaCode(areaCode);
    }

    @Override
    public Complaints changeComplStatus(int complaintId,String status)
    {
        if(Objects.equals(status, "pending")|| Objects.equals(status, "ongoing")|| Objects.equals(status, "solved")) {
            Optional<Complaints> complaint = complaintsRepository.findById(complaintId);
            if (complaint.isEmpty())
                throw new APIRequestException("Complaint with given id not found");
            complaint.get().setStatus(status);
            complaintsRepository.save(complaint.get());
            return complaint.get();
        }
        else
            throw new APIRequestException("status is wrong");
    }
}
