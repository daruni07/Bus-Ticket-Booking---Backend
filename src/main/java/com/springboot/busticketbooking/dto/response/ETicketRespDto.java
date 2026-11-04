package com.springboot.busticketbooking.dto.response;

import java.time.Instant;

public record ETicketRespDto(Long id,
                             String ticketNumber,
                             Instant issuedDate,
                             Long bookingId) {
}
