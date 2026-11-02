package com.springboot.busticketbooking.dto.response;

public record RouteRespDto(
        Long id,
        String source,
        String destination,
        double distance
) {
}
