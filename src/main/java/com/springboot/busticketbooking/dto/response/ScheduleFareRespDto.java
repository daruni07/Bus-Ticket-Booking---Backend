package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.BerthLevel;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.SeatType;

public record ScheduleFareRespDto(Long id,
                                  SeatType seatType,
                                  SeatPosition seatPosition,
                                  BerthLevel berthLevel,
                                  double fare) {
}
