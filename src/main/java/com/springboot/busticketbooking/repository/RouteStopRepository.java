package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.dto.response.RouteRespDto;
import com.springboot.busticketbooking.dto.response.RouteStopRespDto;
import com.springboot.busticketbooking.model.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteStopRepository extends  JpaRepository<RouteStop,Long> {

   @Query("""
 SELECT new com.springboot.busticketbooking.dto.response.RouteStopRespDto(
        rs.id,
        s.id,
        s.stopName,
        s.location,
        rs.stopOrder
    )
    FROM RouteStop rs
    JOIN rs.stop s
    WHERE rs.route.id = ?1
    ORDER BY rs.stopOrder ASC
""")
    List<RouteRespDto> getRouteStop(Long routeStopId);

    @Query("""
    SELECT new com.springboot.busticketbooking.dto.response.RouteStopRespDto(
        rs.id,
        s.id,
        s.stopName,
        s.location,
        rs.stopOrder
    )
    FROM RouteStop rs
    JOIN rs.stop s
   WHERE rs.route.id = ?1
   ORDER BY rs.stopOrder
""")
    List<RouteStopRespDto> getRouteStopsByRouteId(Long routeId);
}
