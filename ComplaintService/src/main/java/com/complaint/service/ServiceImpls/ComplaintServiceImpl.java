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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ComplaintServiceImpl.class);

    //create complaint
    private ComplaintsRepository complaintsRepository;
    private DepartmentRepository departmentRepository;
    @Override
    public Complaints createComplaint(ComplaintDTO complaint)
    {
	logger.info("Creating complaint for department code {}", complaint.getDepartment_code());

        Optional<Departments> department=departmentRepository.findById(complaint.getDepartment_code());
        logger.info("Department found: {}", department);
        if(department.isEmpty()){
            logger.error("Wrong Department code: {}", complaint.getDepartment_code());
            throw new APIRequestException("Wrong Department !");
        }
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
        logger.info("Registered complaint: {}", regcomplaint);
        return complaintsRepository.save(regcomplaint);
    }

    /*
    @Override
    public List<Complaints> getallcomplaints()
    {
        return complaintsRepository.findAll();
    }
    */
   //get complaint with complaint id
    @Override
    public Complaints getComplaint(int complid)
    {
        Optional<Complaints> complaint= complaintsRepository.findById(complid);
        if (complaint.isPresent()) {
            logger.info("Complaint found: {}", complaint.get());
            return complaint.get();
        } else {
            logger.error("Complaint with id {} not found", complid);
            throw new APIRequestException("Complaint with given id not found");
        }
    }

    //get complaints registered by given citizen id
    @Override
    public List<Complaints> getCitizenComplaints(int citizenId)
    {
       List<Complaints> complaints = complaintsRepository.findByCitizenId(citizenId);
        logger.info("Complaints found for citizen id {}: {}", citizenId, complaints);
        return complaints;
    }

   //get complaints in given area code
    @Override
    public  List<Complaints> getAreaComplaints(String areaCode)
    {
	List<Complaints> complaints = complaintsRepository.findByAreaCode(areaCode);
        logger.info("Complaints found for area code {}: {}", areaCode, complaints);
        return complaints;
    }

   // change complaint status of given complaint
    @Override
    public Complaints changeComplStatus(int complaintId,String status)
    {
        if(Objects.equals(status, "pending")|| Objects.equals(status, "ongoing")|| Objects.equals(status, "solved")) {
            Optional<Complaints> complaint = complaintsRepository.findById(complaintId);
            if (complaint.isEmpty()) {
                logger.error("Complaint with id {} not found", complaintId);
                throw new APIRequestException("Complaint with given id not found");
            }
            complaint.get().setStatus(status);
            complaintsRepository.save(complaint.get());
            logger.info("Changed status of complaint id {} to {}", complaintId, status);
            return complaint.get();
        }
        else{
            logger.error("Invalid status provided: {}", status);
            throw new APIRequestException("status is wrong");
        }
    }
}
