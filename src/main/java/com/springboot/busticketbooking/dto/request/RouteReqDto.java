package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RouteReqDto(@NotNull(message = "Source is required")
                          @NotBlank(message = "Source is required")
                          String source,
                          @NotBlank(message = "Destination is required")
                          @NotNull(message = "Destination is required")
                          String destination,
                          @NotNull(message = "Distance is required")
                          Double distance){
}
