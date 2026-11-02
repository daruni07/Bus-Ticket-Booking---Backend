package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.request.RefundReqDto;
import com.springboot.busticketbooking.dto.response.RefundRespDto;
import com.springboot.busticketbooking.model.Refund;
import jakarta.validation.Valid;

public class RefundMapper {
    public static Refund mapToEntity(@Valid RefundReqDto refundReqDto) {
        Refund refund=new Refund();
        refund.setAmount(refundReqDto.amount());
        refund.setRefundDate(refundReqDto.refundDate());
        refund.setRefundStatus(refundReqDto.refundStatus());
        return refund;
    }

    public static RefundRespDto mapToDto(Refund refund) {
        return new RefundRespDto(refund.getId(),refund.getPayment().getBooking().getId(),refund.getPayment().getId(), refund.getAmount(), refund.getRefundStatus(),refund.getRefundDate());
    }
}
