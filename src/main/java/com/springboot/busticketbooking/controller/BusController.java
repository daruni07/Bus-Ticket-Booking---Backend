package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.BusReqDto;
import com.springboot.busticketbooking.dto.request.BusUpdateReqDto;
import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.service.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;
    @PostMapping("/api/bus/add/{busOperatorId}")
    public void insertBus(@RequestBody@Valid BusReqDto busReqDto, @PathVariable Long busOperatorId)
    {
        busService.insertBus(busReqDto,busOperatorId);
    }
    @GetMapping("/api/bus/getAll")
    public List<BusRespDto> getAllBus(@RequestParam(name="page",required = false,defaultValue = "0")Integer page, @RequestParam(name="size",required = false,defaultValue = "10")Integer size)
    {
        return busService.getAllBus(page,size);
    }
    @PutMapping("/api/bus/update/{busId}")
    public void updateBus(@PathVariable Long busId, @Valid BusUpdateReqDto busUpdateReqDto, Principal principal)
    {
        busService.updateBus(busId,busUpdateReqDto,principal.getName());
    }
    @GetMapping("/api/bus/{busId}")
    public BusRespDto getBus(@PathVariable Long busId,Principal principal)
    {
        return busService.getBus(busId,principal.getName());
    }
    @GetMapping("/api/bus/operator")
    public List<BusRespDto> getBusesByOperator(Principal principal) {

        return busService.getBusesByOperator(principal.getName());
    }
}
