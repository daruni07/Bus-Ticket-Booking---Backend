package com.springboot.busticketbooking.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.springboot.busticketbooking.dto.response.PaymentRespDto;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;
import com.springboot.busticketbooking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {


    Optional<Payment> findPaymentByBookingId(long id);

    @Query("""
select p from Payment p where p.booking.id =?1 and p.paymentStatus=?2
""")
    Optional<Payment> findPaymentByBookingIdStatus(long id, PaymentStatus status);

    @Query("""
        SELECT new com.springboot.busticketbooking.dto.response.PaymentRespDto(
            p.id,
            p.booking.id,
            p.booking.totalAmount,
            p.paymentType,
            p.paymentStatus,
            p.transactionId,
            p.paymentDate
        )
        FROM Payment p
        JOIN p.booking b
        JOIN b.schedule s
        JOIN s.bus bus
        JOIN bus.operator bo
        JOIN bo.user u
        WHERE u.username = ?1
          AND (?2 IS NULL
               OR p.paymentStatus = ?2)
          AND (?3 IS NULL
               OR p.paymentType = ?3)
          AND (
              ?4 IS NULL
              OR (
                  p.paymentDate >= ?4
                  AND p.paymentDate < ?5
              ))
              ORDER BY p.paymentDate DESC
""")
    List<PaymentRespDto> dynamicFilterPayments(String username, PaymentStatus paymentStatus, PaymentType paymentType, LocalDateTime dateStart, LocalDateTime dateEnd);
}
