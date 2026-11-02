
package com.springboot.busticketbooking.dto.request;

import com.springboot.busticketbooking.enums.SeatType;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.BerthLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SeatReqDto(

        @NotBlank(message = "Seat number is required")
        String seatNumber,

        @NotNull(message = "Seat type is required")
        SeatType seatType,

        @NotNull(message = "Seat position is required")
        SeatPosition seatPosition,

        @NotNull(message = "Berth level is required")
        BerthLevel berthLevel

) {
}
