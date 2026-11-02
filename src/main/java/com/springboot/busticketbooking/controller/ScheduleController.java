package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.ScheduleReqDto;
import com.springboot.busticketbooking.dto.request.ScheduleUpdateReqDto;
import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.dto.response.BusSearchRespDto;
import com.springboot.busticketbooking.dto.response.ScheduleRespDto;
import com.springboot.busticketbooking.model.Schedule;
import com.springboot.busticketbooking.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping("/api/schedule/add/{busId}/{routeId}")
    public void insertSchedule(@RequestBody @Valid ScheduleReqDto scheduleReqDto, @PathVariable Long busId,@PathVariable Long routeId)
    {
        scheduleService.insertSchedule(scheduleReqDto,busId,routeId);
    }
    @GetMapping("/api/schedules/search")
    public List<BusSearchRespDto> searchBus(@RequestParam String source, @RequestParam String destination, @RequestParam LocalDate travelDate)
    {
        return scheduleService.searchBus(source,destination,travelDate);
    }
    @PatchMapping("/api/schedules/update/{scheduleId}")
    public void updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleUpdateReqDto scheduleUpdateReqDto)
    {
        scheduleService.updateSchedule(scheduleId, scheduleUpdateReqDto);
    }
    @GetMapping("/api/schedule/{scheduleId}")
    public ScheduleRespDto getScheduleById(@PathVariable Long scheduleId)
    {
        return scheduleService.getScheduleById(scheduleId);
    }
}
