package com.user.service.Repositories;

import com.user.service.Entities.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CitizenRepository extends JpaRepository<Citizen,Integer> {

    @Query("select c from Citizen c where c.user.email=:email and c.active=true ")
    Citizen findCitizenByEmail(@Param("email") String email);
}
