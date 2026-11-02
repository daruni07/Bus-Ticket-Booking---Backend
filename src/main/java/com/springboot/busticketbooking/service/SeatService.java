
package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.dto.request.SeatReqDto;
import com.springboot.busticketbooking.mapper.SeatMapper;
import com.springboot.busticketbooking.model.Bus;
import com.springboot.busticketbooking.model.Seat;
import com.springboot.busticketbooking.repository.BusRepository;
import com.springboot.busticketbooking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final BusRepository busRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void insertSeat(SeatReqDto seatReqDto, Long busId, String username) {

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bus id is invalid"));

        if (!bus.getOperator().getUser().getUsername().equals(username)) {
            throw new OwnershipException(
                    "You are not authorized to configure seats for this bus");
        }

        Seat seat = SeatMapper.mapToEntity(seatReqDto);
        seat.setBus(bus);

        seatRepository.save(seat);
    }
}
