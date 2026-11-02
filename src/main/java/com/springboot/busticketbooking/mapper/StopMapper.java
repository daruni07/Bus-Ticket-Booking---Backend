package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.StopReqDto;
import com.springboot.busticketbooking.model.Stop;

public class StopMapper {
    public static Stop mapToEntity(StopReqDto stopReqDto)
    {
        Stop stop=new Stop();
        stop.setStopName(stopReqDto.stopName());
        stop.setLocation(stopReqDto.location());
        return stop;
    }
}
