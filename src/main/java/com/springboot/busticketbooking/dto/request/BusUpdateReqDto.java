package com.springboot.busticketbooking.dto.request;

public record BusUpdateReqDto(
        String busName,
        String busNumber,
        String busType) {
}