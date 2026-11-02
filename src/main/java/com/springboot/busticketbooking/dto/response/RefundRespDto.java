package com.springboot.busticketbooking.dto.response;

import com.springboot.busticketbooking.enums.RefundStatus;

import java.time.LocalDateTime;

public record RefundRespDto (Long refundId,
                             Long paymentId,
                             Long bookingId,
                             double amount,
                             RefundStatus refundStatus,
                             LocalDateTime refundDate){

}
