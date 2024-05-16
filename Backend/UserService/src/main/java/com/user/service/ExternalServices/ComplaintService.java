package com.user.service.ExternalServices;

import com.user.service.DTOs.ComplaintDTO;
import com.user.service.Entities.Complaints;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="ComplaintService")
public interface ComplaintService {

    @GetMapping("/complaint/{citizenId}")
    public ResponseEntity<List<Complaints>> getCitizenComplaints(@PathVariable("citizenId") Integer citizenId);

    @PostMapping("/complaint/")
    public ResponseEntity<Complaints> registerComplaint(ComplaintDTO values);

    @GetMapping("/complaint/area/{areaCode}")
    public ResponseEntity<List<Complaints>> getAreaComplaints(@PathVariable String areaCode);

}
