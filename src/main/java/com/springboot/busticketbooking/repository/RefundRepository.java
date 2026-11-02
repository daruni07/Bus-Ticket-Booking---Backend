package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.enums.RefundStatus;
import com.springboot.busticketbooking.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RefundRepository extends  JpaRepository<Refund,Long> {
   @Query("""
SELECT r FROM Refund r
    WHERE r.refundStatus = ?1
    AND r.payment.booking.schedule.bus.operator.user.username
        = ?2
""")
    List<Refund> getRefunds(RefundStatus refundStatus,String username);
}
