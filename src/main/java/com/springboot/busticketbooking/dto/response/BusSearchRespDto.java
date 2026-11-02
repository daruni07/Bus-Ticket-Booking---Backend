package com.springboot.busticketbooking.dto.response;

import java.time.LocalDateTime;

public record BusSearchRespDto(
        Long scheduleId,
        Long sourceRouteStopId,
        Long destinationRouteStopId,
        String busName,
        String busNumber,
        String busType,
        String sourceStop,
        String destinationStop,
        LocalDateTime departureDatetime,
        LocalDateTime arrivalDatetime
) {}