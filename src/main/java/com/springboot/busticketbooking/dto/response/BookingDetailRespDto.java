package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.GovtIdType;

public record BookingDetailRespDto(String name,
                                   int age,
                                   String gender,
                                   GovtIdType govtIdType,
                                   String seatNumber,
                                   String boardingStop,
                                   String droppingStop) {
}
