package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.dto.response.RouteRespDto;
import com.springboot.busticketbooking.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route,Long> {
    @Query("""
        SELECT new com.springboot.busticketbooking.dto.response.RouteRespDto(
            r.id,
            r.source,
            r.destination,
            r.distance
        )
        FROM Route r
        ORDER BY r.id
    """)
    List<RouteRespDto> getAllRoutes();
}
