
package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.SeatReqDto;
import com.springboot.busticketbooking.model.Seat;

public class SeatMapper {

    public static Seat mapToEntity(SeatReqDto seatReqDto) {

        Seat seat = new Seat();

        seat.setSeatNumber(seatReqDto.seatNumber());
        seat.setSeatType(seatReqDto.seatType());
        seat.setSeatPosition(seatReqDto.seatPosition());
        seat.setBerthLevel(seatReqDto.berthLevel());

        return seat;
    }
}
