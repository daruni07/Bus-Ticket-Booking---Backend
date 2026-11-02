package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.BookingStatus;
import com.springboot.busticketbooking.enums.PaymentStatus;

import java.util.List;

public record BookingRespDto(Long bookingId,
                             java.time.Instant bookingDate,
                             BookingStatus bookingStatus,
                             double totalAmount,
                             Long scheduleId,
                             PaymentStatus paymentStatus,
                             List<BookingDetailRespDto> passengers) {
}
