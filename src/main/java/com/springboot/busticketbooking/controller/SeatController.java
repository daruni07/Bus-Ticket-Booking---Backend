
package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.SeatReqDto;
import com.springboot.busticketbooking.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/api/seat/add/{busId}")
    public void insertSeat(
            @RequestBody @Valid SeatReqDto seatReqDto,
            @PathVariable Long busId,
            Principal principal) {

        seatService.insertSeat(
                seatReqDto,
                busId,
                principal.getName()
        );
    }
}
