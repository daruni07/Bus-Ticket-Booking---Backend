package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.Booking;
import com.springboot.busticketbooking.model.Eticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ETicketRepository extends  JpaRepository<Eticket,Long> {

    @Query("SELECT e FROM Eticket e WHERE e.booking.id = ?1")
    Optional<Eticket> findByBookingId(Long bookingId);
}
