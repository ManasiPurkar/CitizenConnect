package com.complaint.service.Repositories;

import com.complaint.service.Entities.Complaints;
import com.complaint.service.Entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departments,Integer> {
}
