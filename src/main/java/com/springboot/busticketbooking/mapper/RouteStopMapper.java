package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.RouteStopReqDto;
import com.springboot.busticketbooking.model.RouteStop;

public class RouteStopMapper {
    public static RouteStop mapToEntity(RouteStopReqDto routeStopReqDto) {
        RouteStop routeStop=new RouteStop();
        routeStop.setStopOrder(routeStopReqDto.stopOrder());
        return routeStop;
    }
}
