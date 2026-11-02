package com.springboot.busticketbooking.dto.response;

public record RouteStopRespDto(
        Long routeStopId,
        Long stopId,
        String stopName,
        String location,
        Integer stopOrder
) {}
