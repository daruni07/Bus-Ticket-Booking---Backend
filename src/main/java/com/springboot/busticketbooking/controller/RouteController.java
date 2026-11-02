package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.RouteReqDto;
import com.springboot.busticketbooking.dto.request.RouteUpdateReqDto;
import com.springboot.busticketbooking.dto.response.RouteRespDto;
import com.springboot.busticketbooking.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;
    @PostMapping("/api/route/add")
    public void insertRoute(@RequestBody@Valid RouteReqDto routeReqDto)

    {
        routeService.insertRoute(routeReqDto);
    }
    @GetMapping("/api/route/{routeId}")
    public RouteRespDto getRouteById(@PathVariable Long routeId)
    {
        return routeService.getRouteById(routeId);
    }
    @PutMapping("/api/route/update/{routeId}")
    public void updateRoute(@PathVariable Long routeId, @Valid @RequestBody RouteUpdateReqDto dto) {

        routeService.updateRoute(routeId, dto);}
}
