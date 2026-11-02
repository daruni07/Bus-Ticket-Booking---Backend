package com.springboot.busticketbooking.dto.response;

import java.time.LocalDateTime;

public record ScheduleRespDto(Long scheduleId,
                              Long busId,
                              Long routeId, LocalDateTime departureDateTime,
                              LocalDateTime arrivalDateTime) {
}
