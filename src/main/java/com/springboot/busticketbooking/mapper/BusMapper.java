package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.BusReqDto;
import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.model.Bus;

public class BusMapper {
    public static Bus mapToEntity(BusReqDto busReqDto)
    {
        Bus bus=new Bus();
        bus.setBusName(busReqDto.busName());
        bus.setBusNumber(busReqDto.busNumber());
        bus.setBusType(busReqDto.busType());
        bus.setTotalSeats(busReqDto.totalSeats());
        return bus;
    }

    public static BusRespDto mapToDto(Bus bus) {
            return new BusRespDto(bus.getBusName(), bus.getBusType(),bus.getTotalSeats(), bus.getAmenities());
    }


}
