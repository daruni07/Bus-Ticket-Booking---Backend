package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.RouteStopUpdateReqDto;
import com.springboot.busticketbooking.dto.response.RouteRespDto;
import com.springboot.busticketbooking.dto.response.RouteStopRespDto;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.dto.request.RouteStopReqDto;
import com.springboot.busticketbooking.mapper.RouteStopMapper;
import com.springboot.busticketbooking.model.Route;
import com.springboot.busticketbooking.model.RouteStop;
import com.springboot.busticketbooking.model.Stop;
import com.springboot.busticketbooking.repository.RouteRepository;
import com.springboot.busticketbooking.repository.RouteStopRepository;
import com.springboot.busticketbooking.repository.StopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteStopService {
    private final RouteStopRepository routeStopRepository;
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    public void insertRouteStop(RouteStopReqDto routeStopReqDto, Long routeId, Long stopId) {
        Stop stop=stopRepository.findById(stopId).orElseThrow(()->new ResourceNotFoundException("Invalid stop id"));
        Route route=routeRepository.findById(routeId).orElseThrow(()->new ResourceNotFoundException("Invalid route id"));
        RouteStop routeStop= new RouteStop();
        routeStop.setStopOrder(routeStopReqDto.stopOrder());
        routeStop.setStop(stop);
        routeStop.setRoute(route);
        routeStopRepository.save(routeStop);
    }

    public List<RouteRespDto> getRouteStop(Long routeStopId)
    {
           RouteStop route=routeStopRepository.findById(routeStopId)
                   .orElseThrow(()->new ResourceNotFoundException("Invalid route stop id"));
            return routeStopRepository.getRouteStop(routeStopId);
    }

    public void updateRouteStop(Long routeStopId,RouteStopUpdateReqDto routeStopUpdateReqDto) {
        RouteStop routeStop = routeStopRepository.findById(routeStopId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Route stop not found"));
        if(routeStopUpdateReqDto.stopId()!=null) {
            Stop stop = stopRepository.findById(routeStopUpdateReqDto.stopId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Stop not found"));
            routeStop.setStop(stop);
        }
        if(routeStopUpdateReqDto.stopOrder()!=null) {
            routeStop.setStopOrder(routeStopUpdateReqDto.stopOrder());
        }
        routeStopRepository.save(routeStop);
    }

    public List<RouteStopRespDto> getRouteStopsByRouteId(Long routeId) {
        return routeStopRepository.getRouteStopsByRouteId(routeId);
    }
}
