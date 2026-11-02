package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleReqDto (@NotNull(message = "Departure date time is required")
                             LocalDateTime departureDateTime,
                             @NotNull(message = "Arrival date time is required")
                             LocalDateTime arrivalDateTime){
}
