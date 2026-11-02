package com.springboot.busticketbooking.dto.request;

import com.springboot.busticketbooking.enums.SeatStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduleSeatReqDto(
        @NotNull(message = "seat status required")
        @NotBlank(message = "seat status requried")
        SeatStatus seatStatus
) {
}
