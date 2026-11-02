package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.BusOperatorReqDto;
import com.springboot.busticketbooking.dto.request.BusOperatorUpdateReq;
import com.springboot.busticketbooking.dto.response.BusOperatorRespDto;
import com.springboot.busticketbooking.model.BusOperator;
import com.springboot.busticketbooking.service.BusOperatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusOperatorController {
    private final BusOperatorService busOperatorService;
    @PostMapping("/api/busOperator/add")
    public void insertBusOperator(@Valid @RequestBody BusOperatorReqDto busOperatorReqDto)
    {
        busOperatorService.insertBusOperator(busOperatorReqDto);
    }
    @GetMapping("/api/busOperator/get/{busOperatorId}")
    public BusOperatorRespDto getBusById(@PathVariable Long busOperatorId)
    {
        return busOperatorService.getBusById(busOperatorId);
    }
    @GetMapping("/api/busOperator/getAll")
    public List<BusOperatorRespDto> getAllBus()
    {
        return busOperatorService.getAllBus();
    }
    @PutMapping("/api/busOperator/update/me")
    public void updateBusOperator(@Valid@RequestBody BusOperatorUpdateReq busOperatorUpdateReq,Principal principal)
    {
       busOperatorService.updateBusOperator(busOperatorUpdateReq,principal.getName());
    }
}
