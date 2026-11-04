
package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.ScheduleFareReqDto;
import com.springboot.busticketbooking.dto.response.ScheduleFareRespDto;
import com.springboot.busticketbooking.enums.BerthLevel;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.SeatType;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.RouteStop;
import com.springboot.busticketbooking.model.Schedule;
import com.springboot.busticketbooking.model.ScheduleFare;
import com.springboot.busticketbooking.repository.RouteStopRepository;
import com.springboot.busticketbooking.repository.ScheduleFareRepository;
import com.springboot.busticketbooking.repository.ScheduleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleFareService {

    private final ScheduleFareRepository scheduleFareRepository;
    private final ScheduleRepository scheduleRepository;
    private final RouteStopRepository routeStopRepository;

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

    public void insertScheduleFare(@Valid ScheduleFareReqDto dto)
    {
        Schedule schedule = scheduleRepository.findById(dto.scheduleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid schedule ID"));

        RouteStop source = routeStopRepository.findById(dto.sourceRouteStopId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid source route stop ID"));

        RouteStop destination = routeStopRepository.findById(dto.destinationRouteStopId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid destination route stop ID"));

        if (source.getStopOrder() >= destination.getStopOrder()) {
            throw new IllegalArgumentException(
                    "Source stop must come before destination stop");
        }
        ScheduleFare scheduleFare = new ScheduleFare();
        scheduleFare.setSchedule(schedule);
        scheduleFare.setSourceRouteStop(source);
        scheduleFare.setDestinationRouteStop(destination);
        scheduleFare.setSeatType(dto.seatType());
        scheduleFare.setSeatPosition(dto.seatPosition());
        scheduleFare.setBerthLevel(dto.berthLevel());
        scheduleFare.setFare(dto.fare());

        scheduleFareRepository.save(scheduleFare);
    }


    public void updateScheduleFare(Long scheduleFareId, double fare) {
        ScheduleFare scheduleFare = scheduleFareRepository.findById(scheduleFareId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid schedule fare id"));

        scheduleFare.setFare(fare);
        scheduleFareRepository.save(scheduleFare);
    }
}
