package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.RefundReqDto;
import com.springboot.busticketbooking.dto.response.RefundRespDto;
import com.springboot.busticketbooking.enums.RefundStatus;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.RefundMapper;
import com.springboot.busticketbooking.model.Payment;
import com.springboot.busticketbooking.model.Refund;
import com.springboot.busticketbooking.repository.PaymentRepository;
import com.springboot.busticketbooking.repository.RefundRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    public void insertRefund(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid payment id"));

        Refund refund = new Refund();

        refund.setPayment(payment);
        refund.setAmount(payment.getBooking().getTotalAmount());
        refund.setRefundStatus(RefundStatus.PENDING);
        refund.setRefundDate(LocalDateTime.now());

        refundRepository.save(refund);
    }

    public List<RefundRespDto> getRefunds(RefundStatus refundStatus, String username) {
        List<Refund> refundList=refundRepository.getRefunds(refundStatus,username);
        return refundList.stream().map(RefundMapper::mapToDto).toList();
    }

    public void completeRefund(Long refundId, String name) {
        Refund refund=refundRepository.findById(refundId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid refund id"));
        if(!refund.getPayment().getBooking().getSchedule().getBus().getOperator().getUser().getUsername().equals(name))
        {
            throw new OwnershipException("Refund is not accessible by logged in operator");
        }
        refund.setRefundStatus(RefundStatus.COMPLETED);
        refundRepository.save(refund);
    }
}
