package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotNull;

public record RouteStopReqDto(
        @NotNull(message = "Stop order is required")
        Integer stopOrder
) {
}
