package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("""
select b from Booking b where b.passenger.id=?1
""")
    List<Booking> findByPassengerBooking(Long id);

    @Query("""
 SELECT b FROM Booking b
    JOIN b.schedule s
    JOIN s.bus bus
    WHERE bus.operator.id = ?1
""")
    List<Booking> findByOperatorId(Long id);
}
