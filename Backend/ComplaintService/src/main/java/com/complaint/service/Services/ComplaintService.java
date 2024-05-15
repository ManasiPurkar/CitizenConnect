package com.complaint.service.Services;

import com.complaint.service.Entities.Complaints;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComplaintService {
    //create
    Complaints createComplaint(Complaints complaint);
    //get all
    List<Complaints> getallcomplaints();
    //get single
    Complaints getComplaint(int complid);
    List<Complaints> getCitizenComplaints(int citizenId);
}
