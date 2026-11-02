package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.SeatType;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.BerthLevel;
import com.springboot.busticketbooking.enums.SeatStatus;

public record ScheduleSeatRespDto(
        Long seatId,
        String seatNumber,
        SeatType seatType,
        SeatPosition seatPosition,
        BerthLevel berthLevel,
        SeatStatus seatStatus
) {
}