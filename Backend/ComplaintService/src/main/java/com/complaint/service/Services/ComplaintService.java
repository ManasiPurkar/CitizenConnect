package com.complaint.service.Services;

import com.complaint.service.DTOs.ComplaintDTO;
import com.complaint.service.Entities.Complaints;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComplaintService {
    //create
    Complaints createComplaint(ComplaintDTO complaint);
    //get all
   // List<Complaints> getallcomplaints();

    //get single complaint
    Complaints getComplaint(int complid);

    //get citizens complaints
    List<Complaints> getCitizenComplaints(int citizenId);

    //get area complaints
    List<Complaints> getAreaComplaints(String areaCode);
}
