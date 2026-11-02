package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.RouteReqDto;
import com.springboot.busticketbooking.model.Route;

public class RouteMapper {
    public static Route mapToEntity(RouteReqDto routeReqDto)
    {
        Route route=new Route();
        route.setSource(routeReqDto.source());
        route.setDestination(routeReqDto.destination());
        route.setDistance(routeReqDto.distance());
        return route;
    }
}
