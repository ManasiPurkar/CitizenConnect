package com.complaint.service.Repositories;

import com.complaint.service.Entities.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintsRepository extends JpaRepository<Complaints,Integer> {
}
