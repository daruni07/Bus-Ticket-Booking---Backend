package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.dto.response.BusSearchRespDto;
import com.springboot.busticketbooking.dto.response.ScheduleRespDto;
import com.springboot.busticketbooking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    @Query("""
    SELECT DISTINCT new com.springboot.busticketbooking.dto.response.BusSearchRespDto(
        s.id,
        sourceRouteStop.id,
        destinationRouteStop.id,
        b.busName,
        b.busNumber,
        b.busType,
        sourceStop.stopName,
        destinationStop.stopName,
        s.departureDatetime,
        s.arrivalDatetime
    )
    FROM Schedule s
    JOIN s.bus b
    JOIN s.route r
    JOIN RouteStop sourceRouteStop
        ON sourceRouteStop.route = r
    JOIN sourceRouteStop.stop sourceStop
    JOIN RouteStop destinationRouteStop
        ON destinationRouteStop.route = r
    JOIN destinationRouteStop.stop destinationStop
    WHERE LOWER(sourceStop.location) = LOWER(:source)
      AND LOWER(destinationStop.location) = LOWER(:destination)
      AND sourceRouteStop.stopOrder < destinationRouteStop.stopOrder
      AND s.departureDatetime >= :startDate
      AND s.departureDatetime < :endDate
    """)

    List<BusSearchRespDto> searchBus(String source, String destination, LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
    SELECT new com.springboot.busticketbooking.dto.response.ScheduleRespDto(
        s.id,
        s.bus.id,
        s.route.id,
        s.departureDatetime,
        s.arrivalDatetime
    )
    FROM Schedule s
    ORDER BY s.id
""")
    List<ScheduleRespDto> getAllSchedules();
}
