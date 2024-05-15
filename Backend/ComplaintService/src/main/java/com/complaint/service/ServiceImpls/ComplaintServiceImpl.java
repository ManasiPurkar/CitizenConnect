package com.complaint.service.ServiceImpls;

import com.complaint.service.Entities.Complaints;
import com.complaint.service.Repositories.ComplaintsRepository;
import com.complaint.service.Services.ComplaintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableTransactionManagement
public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintsRepository complaintsRepository;
    @Override
    public Complaints createComplaint(Complaints complaint)
    {
        return complaintsRepository.save(complaint);
    }

    @Override
    public List<Complaints> getallcomplaints()
    {
        return complaintsRepository.findAll();
    }

    @Override
    public Complaints getComplaint(int complid)
    {
        Optional<Complaints> complaint= complaintsRepository.findById(complid);
        return complaint.get();
    }
}
