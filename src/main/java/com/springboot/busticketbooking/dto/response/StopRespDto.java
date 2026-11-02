package com.springboot.busticketbooking.dto.response;

public record StopRespDto(
        Long id,
        String stopName,
        String location
) {
}
