package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.BusOperatorReqDto;
import com.springboot.busticketbooking.dto.response.BusOperatorRespDto;
import com.springboot.busticketbooking.model.BusOperator;
import jakarta.validation.Valid;

public class BusOperatorMapper {
    public static BusOperator mapToEntity(@Valid BusOperatorReqDto busOperatorReqDto) {
        BusOperator busOperator=new BusOperator();
        busOperator.setName(busOperatorReqDto.name());
        busOperator.setEmail(busOperatorReqDto.email());
        busOperator.setPhone(busOperatorReqDto.phone());
        return busOperator;
    }

    public static BusOperatorRespDto mapToDto(BusOperator busOperator) {
        return new BusOperatorRespDto(busOperator.getName(), busOperator.getEmail(), busOperator.getPhone());
    }
}
