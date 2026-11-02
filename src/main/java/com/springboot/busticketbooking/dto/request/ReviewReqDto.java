package com.springboot.busticketbooking.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReviewReqDto(@NotNull(message = "Booking id is requires")
                            Long bookingID,
                           @DecimalMin("1.0")
                           @DecimalMax("5.0")
                           @NotNull(message = "rating is required")
                           Double rating,
                           @NotNull(message = " is required")
                           @NotNull(message = "Rating is required")
                           String comment
                           ) {
}
