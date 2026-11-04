package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.response.ETicketRespDto;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.Booking;
import com.springboot.busticketbooking.model.Eticket;
import com.springboot.busticketbooking.repository.ETicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ETicketService {
    private final ETicketRepository eticketRepository;

    public void generateEticket(Booking booking) {

        if (eticketRepository.findByBookingId(booking.getId()).isPresent()) {
            return;
        }

        Eticket eticket = new Eticket();
        eticket.setBooking(booking);
        eticket.setTicketNumber("TKT-" + UUID.randomUUID());
        eticket.setIssuedDate(Instant.now());

        eticketRepository.save(eticket);
    }



    public ETicketRespDto getEticket(Long bookingId,String username) {

        Eticket eticket = eticketRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("E-ticket not found"));

        if (!eticket.getBooking().getPassenger().getUser()
                .getUsername().equals(username)) {

            throw new OwnershipException(
                    "The booking doesn't belong to the logged in user");
        }


        return new ETicketRespDto(
                eticket.getId(),
                eticket.getTicketNumber(),
                eticket.getIssuedDate(),
                eticket.getBooking().getId()
        );
    }
}

