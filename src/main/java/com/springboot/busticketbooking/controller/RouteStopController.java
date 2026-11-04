package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.RouteStopReqDto;
import com.springboot.busticketbooking.dto.request.RouteStopUpdateReqDto;
import com.springboot.busticketbooking.dto.response.RouteRespDto;
import com.springboot.busticketbooking.dto.response.RouteStopRespDto;
import com.springboot.busticketbooking.model.RouteStop;
import com.springboot.busticketbooking.service.RouteStopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RouteStopController {
    private final RouteStopService routeStopService;
    @PostMapping("/api/routeStop/add")
    public void insertRouteStop(RouteStopReqDto routeStopReqDto, @PathVariable Long routeId,@PathVariable Long stopId)
    {
        routeStopService.insertRouteStop(routeStopReqDto,routeId,stopId);
    }
    @GetMapping("/api/route/stops{routeId}")
    public List<RouteRespDto> getRouteStop(@PathVariable Long routeStopId)
    {
        return routeStopService.getRouteStop(routeStopId);
    }
    @PutMapping("/api/routeStop/update/{routeStopId}")
    public void updateRouteStop(@PathVariable Long routeStopId, @Valid @RequestBody RouteStopUpdateReqDto dto)
    {
        routeStopService.updateRouteStop(routeStopId, dto);
    }
    @GetMapping("/api/routeStop/route/{routeId}")
    public List<RouteStopRespDto> getRouteStopsByRouteId(@PathVariable Long routeId) {

        return routeStopService.getRouteStopsByRouteId(routeId);
    }
}

