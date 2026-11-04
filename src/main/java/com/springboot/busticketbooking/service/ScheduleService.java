package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.ScheduleUpdateReqDto;
import com.springboot.busticketbooking.dto.response.BusSearchRespDto;
import com.springboot.busticketbooking.dto.response.ScheduleRespDto;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.dto.request.ScheduleReqDto;
import com.springboot.busticketbooking.mapper.ScheduleMapper;
import com.springboot.busticketbooking.model.Bus;
import com.springboot.busticketbooking.model.Route;
import com.springboot.busticketbooking.model.Schedule;
import com.springboot.busticketbooking.repository.BusRepository;
import com.springboot.busticketbooking.repository.RouteRepository;
import com.springboot.busticketbooking.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final ScheduleRepository scheduleRepository;
    public void insertSchedule(ScheduleReqDto scheduleReqDto, Long busId, Long routeId) {
        Bus bus=busRepository.findById(busId).orElseThrow(()->new ResourceNotFoundException("Invalid bus id"));
        Route route=routeRepository.findById(routeId).orElseThrow(()->new ResourceNotFoundException("Invalid route id"));
        Schedule schedule= ScheduleMapper.mapToEntity(scheduleReqDto);
        schedule.setBus(bus);
        schedule.setRoute(route);
        scheduleRepository.save(schedule);
    }

    public List<BusSearchRespDto> searchBus(String source, String destination, LocalDate travelDate) {

        LocalDateTime startDate = travelDate.atStartOfDay();
        LocalDateTime endDate = travelDate.plusDays(1).atStartOfDay();

        return scheduleRepository.searchBus(source, destination, startDate, endDate);
    }

    public void updateSchedule(Long scheduleId, ScheduleUpdateReqDto scheduleUpdateReqDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Schedule not found"));
        if (scheduleUpdateReqDto.departureDatetime() != null) {
            schedule.setDepartureDatetime(scheduleUpdateReqDto.departureDatetime());
        }
        if (scheduleUpdateReqDto.arrivalDatetime() != null) {
            schedule.setArrivalDatetime(scheduleUpdateReqDto.arrivalDatetime());
        }
        if (!schedule.getArrivalDatetime()
                .isAfter(schedule.getDepartureDatetime())) {
            throw new IllegalArgumentException(
                    "Arrival time must be after departure time");
        }
        scheduleRepository.save(schedule);
    }

    public ScheduleRespDto getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Schedule not found"));
        return new ScheduleRespDto(
                schedule.getId(),
                schedule.getBus().getId(),
                schedule.getRoute().getId(),
                schedule.getDepartureDatetime(),
                schedule.getArrivalDatetime()
        );
    }

    public List<ScheduleRespDto> getAllSchedules() {
        return scheduleRepository.getAllSchedules();
    }
}
