package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.response.ScheduleSeatRespDto;
import com.springboot.busticketbooking.enums.SeatStatus;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.dto.request.ScheduleSeatReqDto;
import com.springboot.busticketbooking.mapper.ScheduleSeatMapper;
import com.springboot.busticketbooking.model.Schedule;
import com.springboot.busticketbooking.model.ScheduleSeat;
import com.springboot.busticketbooking.model.Seat;
import com.springboot.busticketbooking.repository.ScheduleRepository;
import com.springboot.busticketbooking.repository.ScheduleSeatRepository;
import com.springboot.busticketbooking.repository.SeatRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleSeatService {

    private final ScheduleSeatRepository scheduleSeatRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    public void insertScheduleSeat(Long scheduleId, Long seatId) {
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(()->new ResourceNotFoundException("Invalid schedule id"));
        Seat seat=seatRepository.findById(seatId).orElseThrow(()->new ResourceNotFoundException("Invalid seat id"));
        ScheduleSeat scheduleSeat= new ScheduleSeat();
        scheduleSeat.setSchedule(schedule);
        scheduleSeat.setSeat(seat);
        scheduleSeat.setSeatStatus(SeatStatus.AVAILABLE);
        scheduleSeatRepository.save(scheduleSeat);
    }

    public List<ScheduleSeatRespDto> getScheduleSeats(Long scheduleId) {

        List<ScheduleSeat> scheduleSeats =
                scheduleSeatRepository.findByScheduleId(scheduleId);

        return scheduleSeats.stream()
                .map(scheduleSeat -> new ScheduleSeatRespDto(
                        scheduleSeat.getSeat().getId(),
                        scheduleSeat.getSeat().getSeatNumber(),
                        scheduleSeat.getSeat().getSeatType(),
                        scheduleSeat.getSeat().getSeatPosition(),
                        scheduleSeat.getSeat().getBerthLevel(),
                        scheduleSeat.getSeatStatus()
                ))
                .toList();
    }
}
