package com.complaint.service.Repositories;

import com.complaint.service.Entities.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComplaintsRepository extends JpaRepository<Complaints,Integer> {
    List<Complaints> findByCitizenId(Integer citizenId);

    List<Complaints> findByAreaCode(String areaCode);
}
