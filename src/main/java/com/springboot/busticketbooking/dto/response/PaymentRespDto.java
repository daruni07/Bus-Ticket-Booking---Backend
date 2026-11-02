package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;

import java.time.LocalDateTime;

public record PaymentRespDto(Long id,
                             Long bookingId,
                             Double amount,
                             PaymentType paymentType,
                             PaymentStatus paymentStatus,
                             String transactionId,
                             LocalDateTime paymentDate) {
}
