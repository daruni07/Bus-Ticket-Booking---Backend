package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StopReqDto(@NotNull(message = "Stop name is required")
                         @NotBlank(message = "Stop name is required")
                         String stopName,
                         @NotBlank(message = "Location is required")
                         @NotNull(message = "Location is required")
                         String location) {

}
