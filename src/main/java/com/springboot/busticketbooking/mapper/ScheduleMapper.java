package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.ScheduleReqDto;
import com.springboot.busticketbooking.model.Schedule;

public class ScheduleMapper {
    public static Schedule mapToEntity(ScheduleReqDto scheduleReqDto)
    {
        Schedule schedule=new Schedule();
        schedule.setArrivalDatetime(scheduleReqDto.arrivalDateTime());
        schedule.setDepartureDatetime(scheduleReqDto.departureDateTime());
        return schedule;
    }
}
