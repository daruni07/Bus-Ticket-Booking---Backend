package com.springboot.busticketbooking;

import com.springboot.busticketbooking.dto.request.PaymentReqDto;
import com.springboot.busticketbooking.dto.response.PaymentRespDto;
import com.springboot.busticketbooking.enums.BookingStatus;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;
import com.springboot.busticketbooking.enums.SeatStatus;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.Booking;
import com.springboot.busticketbooking.model.BookingDetail;
import com.springboot.busticketbooking.model.Payment;
import com.springboot.busticketbooking.model.ScheduleSeat;
import com.springboot.busticketbooking.repository.BookingDetailRepository;
import com.springboot.busticketbooking.repository.BookingRepository;
import com.springboot.busticketbooking.repository.PaymentRepository;
import com.springboot.busticketbooking.repository.ScheduleSeatRepository;
import com.springboot.busticketbooking.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingDetailRepository bookingDetailRepository;

    @Mock
    private ScheduleSeatRepository scheduleSeatRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void insertPaymentBookingNotFoundTest() {
        PaymentReqDto paymentReqDto = mock(PaymentReqDto.class);

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.insertPayment(paymentReqDto, 1L));

        verify(bookingRepository).findById(1L);
    }

    @Test
    void insertPaymentTest() {
        Booking booking = mock(Booking.class);
        PaymentReqDto paymentReqDto = mock(PaymentReqDto.class);
        BookingDetail detail = mock(BookingDetail.class);
        ScheduleSeat scheduleSeat = mock(ScheduleSeat.class);

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));

        when(paymentReqDto.paymentType()).thenReturn(PaymentType.UPI);

        when(bookingDetailRepository.findByBookingId(1L))
                .thenReturn(List.of(detail));

        when(detail.getScheduleSeat()).thenReturn(scheduleSeat);

        paymentService.insertPayment(paymentReqDto, 1L);

        verify(paymentRepository).save(any(Payment.class));
        verify(booking).setBookingStatus(BookingStatus.CONFIRMED);
        verify(bookingRepository).save(booking);
        verify(bookingDetailRepository).findByBookingId(1L);
        verify(scheduleSeat).setSeatStatus(SeatStatus.BOOKED);
        verify(scheduleSeatRepository).save(scheduleSeat);
    }

    @Test
    void getPaymentByBookingIdNotFoundTest() {
        when(paymentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.getPaymentByBookingId(1L));

        verify(paymentRepository).findById(1L);
    }

    @Test
    void getPaymentByBookingIdTest() {
        Payment payment = mock(Payment.class);
        Booking booking = mock(Booking.class);

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        when(payment.getId()).thenReturn(1L);
        when(payment.getBooking()).thenReturn(booking);
        when(booking.getId()).thenReturn(1L);
        when(booking.getTotalAmount()).thenReturn(1500.0);
        when(payment.getPaymentType()).thenReturn(PaymentType.UPI);
        when(payment.getPaymentStatus()).thenReturn(PaymentStatus.SUCCESS);
        when(payment.getTransactionId()).thenReturn("PAY-123");
        when(payment.getPaymentDate()).thenReturn(LocalDateTime.now());

        PaymentRespDto result = paymentService.getPaymentByBookingId(1L);

        assertNotNull(result);
        verify(paymentRepository).findById(1L);
    }

    @Test
    void dynamicFilterPaymentsTest() {
        when(paymentRepository.dynamicFilterPayments(
                "passenger1",
                PaymentStatus.SUCCESS,
                PaymentType.UPI,
                LocalDate.of(2026, 10, 29).atStartOfDay(),
                LocalDate.of(2026, 10, 30).atStartOfDay()
        )).thenReturn(List.of());

        List<PaymentRespDto> result = paymentService.dynamicFilterPayments(
                PaymentStatus.SUCCESS,
                PaymentType.UPI,
                LocalDate.of(2026, 10, 29),
                "passenger1"
        );

        assertEquals(0, result.size());

        verify(paymentRepository).dynamicFilterPayments(
                "passenger1",
                PaymentStatus.SUCCESS,
                PaymentType.UPI,
                LocalDate.of(2026, 10, 29).atStartOfDay(),
                LocalDate.of(2026, 10, 30).atStartOfDay()
        );
    }

    @Test
    void dynamicFilterPaymentsWithoutDateTest() {
        when(paymentRepository.dynamicFilterPayments(
                "passenger1",
                null,
                null,
                null,
                null
        )).thenReturn(List.of());

        List<PaymentRespDto> result = paymentService.dynamicFilterPayments(
                null, null, null, "passenger1"
        );

        assertEquals(0, result.size());

        verify(paymentRepository).dynamicFilterPayments(
                "passenger1", null, null, null, null
        );
    }
}