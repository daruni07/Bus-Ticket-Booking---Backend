package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.ScheduleFareReqDto;
import com.springboot.busticketbooking.dto.response.ScheduleFareRespDto;
import com.springboot.busticketbooking.service.ScheduleFareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleFareController {
    private final ScheduleFareService scheduleFareService;

    @GetMapping("/api/scheduleFares")
    public List<ScheduleFareRespDto> getScheduleFares(@RequestParam Long scheduleId, @RequestParam Long sourceRouteStopId,
            @RequestParam Long destinationRouteStopId) {

        return scheduleFareService.getScheduleFares(
                scheduleId,
                sourceRouteStopId,
                destinationRouteStopId);
    }
    @PostMapping("/api/scheduleFare/add")
    public void insertScheduleFare(@Valid @RequestBody ScheduleFareReqDto dto) {

        scheduleFareService.insertScheduleFare(dto);
    }
    @PutMapping("/api/scheduleFare/update/{scheduleFareId}")
    public void updateScheduleFare(
            @PathVariable Long scheduleFareId,
            @RequestParam double fare) {

        scheduleFareService.updateScheduleFare(scheduleFareId, fare);
    }
}
