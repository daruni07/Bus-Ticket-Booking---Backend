package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.response.PaymentRespDto;
import com.springboot.busticketbooking.enums.BookingStatus;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;
import com.springboot.busticketbooking.enums.SeatStatus;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.dto.request.PaymentReqDto;
import com.springboot.busticketbooking.model.Booking;
import com.springboot.busticketbooking.model.BookingDetail;
import com.springboot.busticketbooking.model.Payment;
import com.springboot.busticketbooking.model.ScheduleSeat;
import com.springboot.busticketbooking.repository.BookingDetailRepository;
import com.springboot.busticketbooking.repository.BookingRepository;
import com.springboot.busticketbooking.repository.PaymentRepository;
import com.springboot.busticketbooking.repository.ScheduleSeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final ScheduleSeatRepository scheduleSeatRepository;
    public void insertPayment( PaymentReqDto paymentReqDto,Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid booking id"));
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentType(paymentReqDto.paymentType());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId("PAY-" + UUID.randomUUID());
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        List<BookingDetail> details = bookingDetailRepository.findByBookingId(bookingId);

        for (BookingDetail detail : details) {
            ScheduleSeat scheduleSeat = detail.getScheduleSeat();
            scheduleSeat.setSeatStatus(SeatStatus.BOOKED);
            scheduleSeatRepository.save(scheduleSeat);
        }
    }

    public PaymentRespDto getPaymentByBookingId(Long bookingId) {
        Payment payment=paymentRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid id"));
        return new PaymentRespDto(payment.getId(), payment.getBooking().getId(), payment.getBooking().getTotalAmount(),
                payment.getPaymentType(),payment.getPaymentStatus(),payment.getTransactionId(),payment.getPaymentDate());
    }
    public List<PaymentRespDto> dynamicFilterPayments(PaymentStatus paymentStatus, PaymentType paymentType, LocalDate paymentDate, String username) {

        LocalDateTime dateStart = paymentDate == null
                ? null
                : paymentDate.atStartOfDay();

        LocalDateTime dateEnd = paymentDate == null
                ? null
                : paymentDate.plusDays(1).atStartOfDay();

        return paymentRepository.dynamicFilterPayments(
                username,
                paymentStatus,
                paymentType,
                dateStart,
                dateEnd
        );
    }
}
