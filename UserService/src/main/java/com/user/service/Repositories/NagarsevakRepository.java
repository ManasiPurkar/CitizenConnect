package com.user.service.Repositories;

import com.user.service.Entities.Nagarsevak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NagarsevakRepository extends JpaRepository<Nagarsevak,Integer> {
    @Query("select n from Nagarsevak n where n.user.email=:email and n.active=true ")
    Nagarsevak findNagarsevakByEmail(@Param("email") String email);
}
