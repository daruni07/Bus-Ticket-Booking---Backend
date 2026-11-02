package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.StopReqDto;
import com.springboot.busticketbooking.dto.request.StopUpdateReqDto;
import com.springboot.busticketbooking.dto.response.StopRespDto;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.StopMapper;
import com.springboot.busticketbooking.model.Stop;
import com.springboot.busticketbooking.repository.StopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {
    private final StopRepository stopRepository;
    public void insertStop(@Valid StopReqDto stopReqDto) {
        Stop stop= StopMapper.mapToEntity(stopReqDto);
        stopRepository.save(stop);
    }

    public List<StopRespDto> getAllStop() {
        return stopRepository.getAllStop();
    }

    public void updateStop(Long stopId, @Valid StopUpdateReqDto stopUpdateReqDto) {
        Stop stop = stopRepository.findById(stopId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Stop not found"));
        if(stopUpdateReqDto.stopName()!=null) {
            stop.setStopName(stopUpdateReqDto.stopName());
        }
        if(stopUpdateReqDto.location()!=null){
            stop.setLocation(stopUpdateReqDto.location());
        }
        stopRepository.save(stop);
    }
}
