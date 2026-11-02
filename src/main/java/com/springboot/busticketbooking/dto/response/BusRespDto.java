package com.springboot.busticketbooking.dto.response;

public record BusRespDto(String busName,
                         String busType,
                         Integer totalSeats,
                         String amenities) {
}
