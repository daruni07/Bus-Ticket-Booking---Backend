package com.springboot.busticketbooking.dto.request;

import com.springboot.busticketbooking.enums.GovtIdType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TravellerReqDto(@NotBlank(message="name is required")
                              @NotNull(message="name is required")
                               String name,
                              @NotNull(message = "age is required")
                               Integer age,
                              @NotBlank(message = "gender is required")
                               String gender,
                              @NotNull(message = "govt id type is required")
                              GovtIdType govtIdType,
                              @NotBlank(message = "govtIdProof is required")
                               String govtIdProof,
                              @NotNull(message = "schedule seat id is required" )
                               Long scheduleSeatId,
                              @NotNull(message = "boarding stop is required")
                               Long boardingRouteStopId,
                              @NotNull(message = "dropping stop is required")
                               Long droppingRouteStopId) {
}
