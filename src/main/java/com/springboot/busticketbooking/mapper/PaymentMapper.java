package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.PaymentReqDto;
import com.springboot.busticketbooking.model.Payment;

public class PaymentMapper {
    public static Payment mapToEntity(PaymentReqDto paymentReqDto) {
        Payment payment=new Payment();
        payment.setPaymentType(paymentReqDto.paymentType());
        return payment;
    }
}
