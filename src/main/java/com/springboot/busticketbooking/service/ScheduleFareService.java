
package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.response.ScheduleFareRespDto;
import com.springboot.busticketbooking.enums.BerthLevel;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.SeatType;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.ScheduleFare;
import com.springboot.busticketbooking.repository.ScheduleFareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleFareService {

    private final ScheduleFareRepository scheduleFareRepository;

    public ScheduleFare getScheduleFare(
            Long scheduleId,
            Long sourceRouteStopId,
            Long destinationRouteStopId,
            SeatType seatType,
            SeatPosition seatPosition,
            BerthLevel berthLevel) {
        return scheduleFareRepository.getScheduleFare(
                scheduleId,
                sourceRouteStopId,
                destinationRouteStopId,
                seatType,
                seatPosition,
                berthLevel
        ).orElseThrow(() ->
                new ResourceNotFoundException("Cannot fetch fare"));
    }
    public List<ScheduleFareRespDto> getScheduleFares(
            Long scheduleId,
            Long sourceRouteStopId,
            Long destinationRouteStopId) {

        List<ScheduleFareRespDto> scheduleFares =
                scheduleFareRepository
                        .getScheduleFares(
                                scheduleId,
                                sourceRouteStopId,
                                destinationRouteStopId
                        );

        if (scheduleFares.isEmpty()) {
            throw new ResourceNotFoundException("Cannot fetch fares");
        }

        return scheduleFares;
    }
}
