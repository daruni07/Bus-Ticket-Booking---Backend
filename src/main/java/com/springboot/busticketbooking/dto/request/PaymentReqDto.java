package com.springboot.busticketbooking.dto.request;

import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PaymentReqDto(
                            @NotNull(message = "Payment Type is required")
                            PaymentType paymentType
                           ) {
}
