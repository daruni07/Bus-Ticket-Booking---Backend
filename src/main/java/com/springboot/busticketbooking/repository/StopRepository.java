package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.dto.response.StopRespDto;
import com.springboot.busticketbooking.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StopRepository extends JpaRepository<Stop,Long> {
    @Query("""
SELECT new com.springboot.busticketbooking.dto.response.StopRespDto(
        s.id,
        s.stopName,
        s.location)
        FROM Stop s ORDER BY s.stopName
""")
    List<StopRespDto> getAllStop();
}
