package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.BusUpdateReqDto;
import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.dto.request.BusReqDto;
import com.springboot.busticketbooking.mapper.BusMapper;
import com.springboot.busticketbooking.model.Bus;
import com.springboot.busticketbooking.model.BusOperator;
import com.springboot.busticketbooking.repository.BusOperatorRepository;
import com.springboot.busticketbooking.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BusService {
    private final BusRepository busRepository;
    private final BusOperatorRepository busOperatorRepository;
    public void insertBus(BusReqDto busReqDto, Long busOperatorId) {
        BusOperator busOperator=busOperatorRepository.findById(busOperatorId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid bus operator id"));
        Bus bus= BusMapper.mapToEntity(busReqDto);
        bus.setOperator(busOperator);
        busRepository.save(bus);
    }

    public List<BusRespDto> getAllBus(Integer page,Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        List<Bus> busList=busRepository.getAllBus(pageable);
        return  busList.stream().map(BusMapper::mapToDto).toList();
    }
    public void updateBus(Long busId, BusUpdateReqDto busUpdateReqDto,String name) {

            Bus bus = busRepository.findById(busId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Bus not found"));
        if(!bus.getOperator().getUser().getUsername().equals(name))
        {
            throw new OwnershipException("Bus details can't be updated by logged in operator");
        }
            if (busUpdateReqDto.busName() != null) {
                bus.setBusName(busUpdateReqDto.busName());
            }

            if (busUpdateReqDto.busNumber() != null) {
                bus.setBusNumber(busUpdateReqDto.busNumber());
            }

            if (busUpdateReqDto.busType() != null) {
                bus.setBusType(busUpdateReqDto.busType());
            }
            busRepository.save(bus);
        }

    public BusRespDto getBus(Long busId,String username) {
        Bus bus=busRepository.findById(busId)
                .orElseThrow(()->new ResourceNotFoundException("Bus id invalid"));
        if(!bus.getOperator().getUser().getUsername().equals(username))
        {
            throw new OwnershipException("Bus details can't be viewed by logged in operator");
        }
        return BusMapper.mapToDto(bus);
    }
}
