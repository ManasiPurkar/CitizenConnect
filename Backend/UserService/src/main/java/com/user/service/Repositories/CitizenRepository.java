package com.user.service.Repositories;

import com.user.service.Entities.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen,Integer> {
}
