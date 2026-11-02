
package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.dto.response.ScheduleFareRespDto;
import com.springboot.busticketbooking.enums.BerthLevel;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.SeatType;
import com.springboot.busticketbooking.model.ScheduleFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleFareRepository
        extends JpaRepository<ScheduleFare, Long> {

    @Query("""
        SELECT sf
        FROM ScheduleFare sf
        WHERE sf.schedule.id = :scheduleId
          AND sf.sourceRouteStop.id = :sourceRouteStopId
          AND sf.destinationRouteStop.id = :destinationRouteStopId
          AND sf.seatType = :seatType
          AND sf.seatPosition = :seatPosition
          AND sf.berthLevel = :berthLevel
        """)
    Optional<ScheduleFare> getScheduleFare(
            @Param("scheduleId") Long scheduleId,
            @Param("sourceRouteStopId") Long sourceRouteStopId,
            @Param("destinationRouteStopId") Long destinationRouteStopId,
            @Param("seatType") SeatType seatType,
            @Param("seatPosition") SeatPosition seatPosition,
            @Param("berthLevel") BerthLevel berthLevel
    );

    @Query("""
    SELECT new com.springboot.busticketbooking.dto.response.ScheduleFareRespDto(
        sf.id,
        sf.seatType,
        sf.seatPosition,
        sf.berthLevel,
        sf.fare
    )
    FROM ScheduleFare sf
    WHERE sf.schedule.id = :scheduleId
      AND sf.sourceRouteStop.id = :sourceRouteStopId
      AND sf.destinationRouteStop.id = :destinationRouteStopId
    """)

    List<ScheduleFareRespDto> getScheduleFares(Long scheduleId, Long sourceRouteStopId, Long destinationRouteStopId);
}
