package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.BusOperatorReqDto;
import com.springboot.busticketbooking.dto.request.BusOperatorUpdateReq;
import com.springboot.busticketbooking.dto.response.BusOperatorRespDto;
import com.springboot.busticketbooking.enums.Role;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.BusOperatorMapper;
import com.springboot.busticketbooking.model.BusOperator;
import com.springboot.busticketbooking.model.User;
import com.springboot.busticketbooking.repository.BusOperatorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusOperatorService {
    private final BusOperatorRepository busOperatorRepository;
    public void insertBusOperator(@Valid BusOperatorReqDto busOperatorReqDto) {
        User user=new User();
        user.setUsername(busOperatorReqDto.username());
        user.setPassword(busOperatorReqDto.password());
        user.setRole(Role.BUS_OPERATOR);
        BusOperator busOperator= BusOperatorMapper.mapToEntity(busOperatorReqDto);
        busOperator.setUser(user);
        busOperatorRepository.save(busOperator);
    }

    public BusOperatorRespDto getBusById(Long busOperatorId) {
        BusOperator busOperator=busOperatorRepository.findById(busOperatorId).orElseThrow(()->new ResourceNotFoundException("Invalid bus operator id"));
        return BusOperatorMapper.mapToDto(busOperator);
    }

    public List<BusOperatorRespDto> getAllBus() {
        List<BusOperator> busOperators=busOperatorRepository.findAll();
        return busOperators.stream().map(BusOperatorMapper::mapToDto).toList();
    }


    public void updateBusOperator(BusOperatorUpdateReq busOperatorUpdateReq,String name) {
        Optional<BusOperator> busOperatorOptional = busOperatorRepository.findByUserName(name);
           if(busOperatorOptional.isEmpty())
           {
                     throw  new ResourceNotFoundException("Bus operator not found");
           }
           BusOperator busOperator=busOperatorOptional.get();
        if (busOperatorUpdateReq.name() != null) {
            busOperator.setName(busOperatorUpdateReq.name());
        }

        if (busOperatorUpdateReq.email() != null) {
            busOperator.setEmail(busOperatorUpdateReq.email());
        }

        if (busOperatorUpdateReq.phone() != null) {
            busOperator.setPhone(busOperatorUpdateReq.phone());
        }
        busOperatorRepository.save(busOperator);
    }
}
