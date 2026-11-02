package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.PaymentReqDto;
import com.springboot.busticketbooking.dto.response.PaymentRespDto;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;
import com.springboot.busticketbooking.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/api/payment/add/{bookingId}")
    public void insertPayment(@RequestBody @Valid PaymentReqDto paymentReqDto, @PathVariable Long bookingId)
    {
        paymentService.insertPayment(paymentReqDto,bookingId);
    }
    @GetMapping("/api/payment/booking/{bookingId}")
    public PaymentRespDto getPaymentByBookingId(@PathVariable Long bookingId) {
        return paymentService.getPaymentByBookingId(bookingId);
    }
    @GetMapping("/api/payment/operator/filter")
    public List<PaymentRespDto> dynamicFilterPayments(@RequestParam(required = false) PaymentStatus paymentStatus,
                                                      @RequestParam(required = false) PaymentType paymentType,
                                                      @RequestParam(required = false) LocalDate paymentDate,
                                                      Principal principal) {
        return paymentService.dynamicFilterPayments(
                paymentStatus,
                paymentType,
                paymentDate,
                principal.getName()
        );
    }

}
