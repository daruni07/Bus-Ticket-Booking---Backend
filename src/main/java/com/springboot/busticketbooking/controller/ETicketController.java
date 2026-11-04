package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.response.ETicketRespDto;
import com.springboot.busticketbooking.service.ETicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ETicketController {
    private final ETicketService eticketService;

    @GetMapping("/{bookingId}")
    public ETicketRespDto getEticket(@PathVariable Long bookingId, Principal principal) {
        return eticketService.getEticket(bookingId,principal.getName());
    }
}
