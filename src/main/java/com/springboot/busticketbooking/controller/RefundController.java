package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.RefundReqDto;
import com.springboot.busticketbooking.dto.response.RefundRespDto;
import com.springboot.busticketbooking.enums.RefundStatus;
import com.springboot.busticketbooking.model.Refund;
import com.springboot.busticketbooking.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RefundController {
    private final RefundService refundService;
    @PostMapping("/api/refund/add/{paymentId}")
    public void insertRefund(@PathVariable Long paymentId)
    {
        refundService.insertRefund(paymentId);
    }
    @GetMapping("/api/refund/getRefunds")
    public List<RefundRespDto> getRefunds(@RequestParam RefundStatus refundStatus, Principal principal)
    {
        return refundService.getRefunds(refundStatus,principal.getName());
    }
    @PutMapping("/api/refund/complete/{refundId}")
    public void completeRefund(@PathVariable Long refundId, Principal principal) {
        refundService.completeRefund(refundId, principal.getName());
    }
}
