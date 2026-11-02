package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.ScheduleSeatReqDto;
import com.springboot.busticketbooking.dto.response.ScheduleSeatRespDto;
import com.springboot.busticketbooking.service.ScheduleSeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleSeatController {
    private final ScheduleSeatService scheduleSeatService;
    @PostMapping("/api/scheduleSeat/add/{scheduleId}/{seatId}")
    public void insertScheduleSeat(@PathVariable Long scheduleId,@PathVariable Long seatId)
    {
        scheduleSeatService.insertScheduleSeat(scheduleId,seatId);
    }
    @GetMapping("/api/schedule/seats/{scheduleId}")
    public List<ScheduleSeatRespDto> getScheduleSeats(@PathVariable Long scheduleId) {

        return scheduleSeatService.getScheduleSeats(scheduleId);
    }
}
