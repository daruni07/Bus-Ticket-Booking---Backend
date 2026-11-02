package com.springboot.busticketbooking.dto.request;

import java.time.LocalDateTime;

public record ScheduleUpdateReqDto(
        LocalDateTime departureDatetime,
        LocalDateTime arrivalDatetime
) {
}
