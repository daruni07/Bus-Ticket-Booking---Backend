package com.springboot.busticketbooking.dto.request;

import com.springboot.busticketbooking.enums.RefundStatus;
import jakarta.validation.constraints.NotNull;

import javax.management.relation.RoleStatus;
import java.time.LocalDateTime;

public record RefundReqDto(@NotNull(message = "Amount is required")
                           Double amount,
                           @NotNull(message = "Role status is required")
                           RefundStatus refundStatus,
                           @NotNull(message = "Date is required")
                           LocalDateTime refundDate
                          ) {
}
