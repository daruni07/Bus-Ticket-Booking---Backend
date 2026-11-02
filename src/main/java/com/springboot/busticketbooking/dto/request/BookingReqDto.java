package com.springboot.busticketbooking.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookingReqDto (@NotNull
                               Long scheduleId,

                             @NotEmpty(message = "At least one traveller is required")
                             List<@Valid TravellerReqDto> passengers
){

}
