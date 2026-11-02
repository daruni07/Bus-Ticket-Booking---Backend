package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail,Long> {
  @Query("""
 select b from BookingDetail b where b.booking.id=?1
 """)
    List<BookingDetail> findByBookingId(Long bookingId);
}
