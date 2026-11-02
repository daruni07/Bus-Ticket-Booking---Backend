package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.ScheduleSeatReqDto;
import com.springboot.busticketbooking.model.ScheduleSeat;
import jakarta.validation.Valid;

public class ScheduleSeatMapper {
    public static ScheduleSeat mapToEntity(@Valid ScheduleSeatReqDto scheduleSeatReqDto) {
        ScheduleSeat scheduleSeat=new ScheduleSeat();
        scheduleSeat.setSeatStatus(scheduleSeatReqDto.seatStatus());
        return scheduleSeat;
    }
}
