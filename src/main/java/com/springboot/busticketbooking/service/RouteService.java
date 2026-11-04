package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.RouteReqDto;
import com.springboot.busticketbooking.dto.request.RouteUpdateReqDto;
import com.springboot.busticketbooking.dto.response.RouteRespDto;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.RouteMapper;
import com.springboot.busticketbooking.model.Route;
import com.springboot.busticketbooking.repository.RouteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    public void insertRoute(RouteReqDto routeReqDto) {
        Route route= RouteMapper.mapToEntity(routeReqDto);
        routeRepository.save(route);
    }

    public RouteRespDto getRouteById(Long routeId) {
        Route route=routeRepository.findById(routeId)
                .orElseThrow(()->new ResourceNotFoundException("Route not found"));
        return new RouteRespDto(route.getId(),route.getSource(),route.getDestination(),route.getDistance());
    }

    public void updateRoute(Long routeId, @Valid RouteUpdateReqDto routeUpdateReqDto) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Route not found"));
        if(routeUpdateReqDto.source()!=null) {
            route.setSource(routeUpdateReqDto.source());
        }
        if(routeUpdateReqDto.destination()!=null)
        {
        route.setDestination(routeUpdateReqDto.destination());}
        if(routeUpdateReqDto.distance()!=null) {
            route.setDistance(routeUpdateReqDto.distance());
        }
        routeRepository.save(route);
    }

    public List<RouteRespDto> getAllRoutes() {
       return routeRepository.getAllRoutes();
    }
}
