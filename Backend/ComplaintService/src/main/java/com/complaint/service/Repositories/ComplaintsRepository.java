package com.complaint.service.Repositories;

import com.complaint.service.Entities.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintsRepository extends JpaRepository<Complaints,Integer> {
    List<Complaints> findByCitizenId(Integer citizenId);
}
