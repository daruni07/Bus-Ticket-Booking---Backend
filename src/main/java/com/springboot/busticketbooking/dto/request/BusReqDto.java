package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BusReqDto(@NotNull(message = "Bus name is required")
                        @NotBlank(message = "bus name is required")
                        String busName,
                        @NotBlank(message = "bus number is required")
                        @NotNull(message = "bus number is required")
                        String busNumber,
                        @NotNull(message = "Bus type is required")
                        @NotBlank(message = "Bus type is required")
                        String busType,
                        @NotNull(message = "Total seats is required")
                        Integer totalSeats
                        ) {
}
