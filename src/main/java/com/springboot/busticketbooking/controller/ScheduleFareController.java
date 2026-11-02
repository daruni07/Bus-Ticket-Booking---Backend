package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.response.ScheduleFareRespDto;
import com.springboot.busticketbooking.service.ScheduleFareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
