package com.springboot.busticketbooking.dto.request;

import com.springboot.busticketbooking.enums.BerthLevel;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.SeatType;
import jakarta.validation.constraints.NotNull;

public record ScheduleFareReqDto(

        @NotNull(message = "Schedule ID is required")
        Long scheduleId,
        @NotNull(message = "Source route stop ID is required")
        Long sourceRouteStopId,
        @NotNull(message = "Destination route stop ID is required")
        Long destinationRouteStopId,
        @NotNull(message = "Seat type is required")
        SeatType seatType,
        @NotNull(message = "Seat position is required")
        SeatPosition seatPosition,
        @NotNull(message = "Berth level is required")
        BerthLevel berthLevel,
        @NotNull(message = "Fare is required")
        Double fare
) {}
