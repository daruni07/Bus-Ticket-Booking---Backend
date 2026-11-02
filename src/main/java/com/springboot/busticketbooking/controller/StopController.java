package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.StopReqDto;
import com.springboot.busticketbooking.dto.request.StopUpdateReqDto;
import com.springboot.busticketbooking.dto.response.StopRespDto;
import com.springboot.busticketbooking.service.StopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StopController {
    private final StopService stopService;
    @PostMapping("/api/stop/add")
    public void insertStop(@RequestBody @Valid StopReqDto stopReqDto)
    {
        stopService.insertStop(stopReqDto);
    }

    @GetMapping("/api/stop/getAll")
    public List<StopRespDto> getAllStop()
    {
        return stopService.getAllStop();
    }
    @PutMapping("/api/stop/update/{stopId}")
    public void updateStop(@PathVariable Long stopId, @Valid @RequestBody StopUpdateReqDto stopUpdateReqDto) {

        stopService.updateStop(stopId, stopUpdateReqDto);
    }
}
