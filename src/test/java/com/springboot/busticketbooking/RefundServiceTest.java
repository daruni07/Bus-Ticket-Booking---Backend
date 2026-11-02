package com.springboot.busticketbooking;

import com.springboot.busticketbooking.dto.response.RefundRespDto;
import com.springboot.busticketbooking.enums.RefundStatus;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.*;
import com.springboot.busticketbooking.repository.PaymentRepository;
import com.springboot.busticketbooking.repository.RefundRepository;
import com.springboot.busticketbooking.service.RefundService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefundServiceTest {

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private RefundService refundService;

    @Test
    void insertRefundPaymentNotFoundTest() {
        when(paymentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> refundService.insertRefund(1L));

        verify(paymentRepository).findById(1L);
    }

    @Test
    void insertRefundTest() {
        Payment payment = mock(Payment.class);
        Booking booking = mock(Booking.class);

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        when(payment.getBooking()).thenReturn(booking);
        when(booking.getTotalAmount()).thenReturn(1500.0);

        refundService.insertRefund(1L);

        verify(paymentRepository).findById(1L);
        verify(refundRepository).save(any(Refund.class));
    }

    @Test
    void getRefundsTest() {
        Refund refund1 = mock(Refund.class);
        Refund refund2 = mock(Refund.class);

        when(refundRepository.getRefunds(
                RefundStatus.PENDING, "operator1"))
                .thenReturn(List.of(refund1, refund2));

        List<RefundRespDto> result = refundService.getRefunds(
                RefundStatus.PENDING, "operator1");

        assertEquals(2, result.size());

        verify(refundRepository).getRefunds(
                RefundStatus.PENDING, "operator1");
    }

    @Test
    void completeRefundNotFoundTest() {
        when(refundRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> refundService.completeRefund(1L, "operator1"));

        verify(refundRepository).findById(1L);
    }

    @Test
    void completeRefundWrongOwnerTest() {
        Refund refund = mock(Refund.class);
        Payment payment = mock(Payment.class);
        Booking booking = mock(Booking.class);
        Schedule schedule = mock(Schedule.class);
        Bus bus = mock(Bus.class);
        BusOperator busOperator = mock(BusOperator.class);
        User user = mock(User.class);

        when(refundRepository.findById(1L))
                .thenReturn(Optional.of(refund));

        when(refund.getPayment()).thenReturn(payment);
        when(payment.getBooking()).thenReturn(booking);
        when(booking.getSchedule()).thenReturn(schedule);
        when(schedule.getBus()).thenReturn(bus);
        when(bus.getOperator()).thenReturn(busOperator);
        when(busOperator.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("operator1");

        assertThrows(OwnershipException.class,
                () -> refundService.completeRefund(1L, "operator2"));

        verify(refundRepository).findById(1L);
    }

    @Test
    void completeRefundTest() {
        Refund refund = mock(Refund.class);
        Payment payment = mock(Payment.class);
        Booking booking = mock(Booking.class);
        Schedule schedule = mock(Schedule.class);
        Bus bus = mock(Bus.class);
        BusOperator busOperator = mock(BusOperator.class);
        User user = mock(User.class);

        when(refundRepository.findById(1L))
                .thenReturn(Optional.of(refund));

        when(refund.getPayment()).thenReturn(payment);
        when(payment.getBooking()).thenReturn(booking);
        when(booking.getSchedule()).thenReturn(schedule);
        when(schedule.getBus()).thenReturn(bus);
        when(bus.getOperator()).thenReturn(busOperator);
        when(busOperator.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("operator1");

        refundService.completeRefund(1L, "operator1");

        verify(refund).setRefundStatus(RefundStatus.COMPLETED);
        verify(refundRepository).save(refund);
    }
}
