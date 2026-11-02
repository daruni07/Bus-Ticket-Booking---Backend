
package com.springboot.busticketbooking;

import com.springboot.busticketbooking.dto.request.BookingReqDto;
import com.springboot.busticketbooking.dto.request.TravellerReqDto;
import com.springboot.busticketbooking.dto.response.BookingRespDto;
import com.springboot.busticketbooking.enums.BookingStatus;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.SeatStatus;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.model.*;
import com.springboot.busticketbooking.repository.*;
import com.springboot.busticketbooking.service.BookingService;
import com.springboot.busticketbooking.service.RefundService;
import com.springboot.busticketbooking.service.ScheduleFareService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ScheduleSeatRepository scheduleSeatRepository;

    @Mock
    RouteStopRepository routeStopRepository;

    @Mock
    BookingDetailRepository bookingDetailRepository;

    @Mock
    ScheduleFareService scheduleFareService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    RefundService refundService;


    @Test
    void createBookingInvalidPassengerTest() {

        BookingReqDto request = mock(BookingReqDto.class);

        when(passengerRepository.findByUsername("alex"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.createBooking(request, "alex"));

        verify(passengerRepository, times(1)).findByUsername("alex");
        verifyNoMoreInteractions(passengerRepository);

        verifyNoInteractions(scheduleRepository, scheduleSeatRepository,
                routeStopRepository, bookingDetailRepository,
                scheduleFareService, bookingRepository);
    }


    @Test
    void createBookingInvalidScheduleTest() {

        BookingReqDto request = mock(BookingReqDto.class);
        Passenger passenger = mock(Passenger.class);

        when(passengerRepository.findByUsername("alex"))
                .thenReturn(Optional.of(passenger));

        when(request.scheduleId()).thenReturn(1L);

        when(scheduleRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.createBooking(request, "alex"));

        verify(passengerRepository).findByUsername("alex");
        verify(scheduleRepository).findById(1L);

        verifyNoInteractions(scheduleSeatRepository, routeStopRepository,
                bookingDetailRepository, scheduleFareService,
                bookingRepository);
    }


    @Test
    void createBookingSeatNotAvailableTest() {

        BookingReqDto request = mock(BookingReqDto.class);
        TravellerReqDto traveller = mock(TravellerReqDto.class);

        Passenger passenger = mock(Passenger.class);
        Schedule schedule = mock(Schedule.class);
        ScheduleSeat seat = mock(ScheduleSeat.class);

        when(passengerRepository.findByUsername("alex"))
                .thenReturn(Optional.of(passenger));

        when(request.scheduleId()).thenReturn(1L);
        when(request.passengers()).thenReturn(List.of(traveller));

        when(scheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule));

        when(traveller.scheduleSeatId()).thenReturn(10L);

        when(scheduleSeatRepository.findById(10L))
                .thenReturn(Optional.of(seat));

        when(seat.getSeatStatus()).thenReturn(SeatStatus.BOOKED);

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.createBooking(request, "alex"));

        verify(scheduleSeatRepository).findById(10L);
        verify(scheduleSeatRepository, never()).save(any());

        verifyNoInteractions(routeStopRepository, bookingDetailRepository,
                scheduleFareService, bookingRepository);
    }


    @Test
    void getBookingHistoryPassengerNotFoundTest() {

        when(passengerRepository.findByUsername("alex"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.getBookingHistory("alex"));

        verify(passengerRepository).findByUsername("alex");

        verifyNoInteractions(bookingRepository, bookingDetailRepository,
                paymentRepository);
    }


    @Test
    void getBookingHistoryTest() {

        Passenger passenger = mock(Passenger.class);
        Booking booking = mock(Booking.class);
        BookingDetail detail = mock(BookingDetail.class);
        Payment payment = mock(Payment.class);

        when(passengerRepository.findByUsername("alex"))
                .thenReturn(Optional.of(passenger));

        when(passenger.getId()).thenReturn(1L);

        when(bookingRepository.findByPassengerBooking(1L))
                .thenReturn(List.of(booking));

        when(booking.getId()).thenReturn(10L);

        when(bookingDetailRepository.findByBookingId(10L))
                .thenReturn(List.of(detail));

        when(paymentRepository.findPaymentByBookingId(10L))
                .thenReturn(Optional.of(payment));

        when(payment.getPaymentStatus()).thenReturn(PaymentStatus.SUCCESS);

        List<BookingRespDto> result =
                bookingService.getBookingHistory("alex");

        assertEquals(1, result.size());

        verify(passengerRepository).findByUsername("alex");
        verify(bookingRepository).findByPassengerBooking(1L);
        verify(bookingDetailRepository).findByBookingId(10L);
        verify(paymentRepository).findPaymentByBookingId(10L);
    }


    @Test
    void getBookingByIdNotFoundTest() {

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.getBookingById(10L));

        verify(bookingRepository).findById(10L);

        verifyNoInteractions(bookingDetailRepository, paymentRepository);
    }


    @Test
    void getBookingByIdTest() {

        Booking booking = mock(Booking.class);
        BookingDetail detail = mock(BookingDetail.class);
        Payment payment = mock(Payment.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getId()).thenReturn(10L);

        when(bookingDetailRepository.findByBookingId(10L))
                .thenReturn(List.of(detail));

        when(paymentRepository.findPaymentByBookingId(10L))
                .thenReturn(Optional.of(payment));

        when(payment.getPaymentStatus()).thenReturn(PaymentStatus.SUCCESS);

        BookingRespDto result = bookingService.getBookingById(10L);

        assertNotNull(result);

        verify(bookingRepository).findById(10L);
        verify(bookingDetailRepository).findByBookingId(10L);
        verify(paymentRepository).findPaymentByBookingId(10L);
    }


    @Test
    void getBookingByIdPaymentNotFoundTest() {

        Booking booking = mock(Booking.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getId()).thenReturn(10L);

        when(bookingDetailRepository.findByBookingId(10L))
                .thenReturn(List.of());

        when(paymentRepository.findPaymentByBookingId(10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.getBookingById(10L));

        verify(bookingDetailRepository).findByBookingId(10L);
        verify(paymentRepository).findPaymentByBookingId(10L);
    }


    @Test
    void cancelBookingNotFoundTest() {

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.cancelBooking(10L, "alex"));

        verify(bookingRepository).findById(10L);

        verifyNoInteractions(paymentRepository, bookingDetailRepository,
                scheduleSeatRepository, refundService);
    }


    @Test
    void cancelBookingWrongOwnerTest() {

        Booking booking = mock(Booking.class);
        Passenger passenger = mock(Passenger.class);
        User user = mock(User.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getPassenger()).thenReturn(passenger);
        when(passenger.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("alex");

        assertThrows(OwnershipException.class,
                () -> bookingService.cancelBooking(10L, "john"));

        verify(bookingRepository).findById(10L);

        verifyNoInteractions(paymentRepository, bookingDetailRepository,
                scheduleSeatRepository, refundService);
    }


    @Test
    void cancelBookingNotConfirmedTest() {

        Booking booking = mock(Booking.class);
        Passenger passenger = mock(Passenger.class);
        User user = mock(User.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getPassenger()).thenReturn(passenger);
        when(passenger.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("alex");

        when(booking.getBookingStatus()).thenReturn(BookingStatus.PENDING);

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.cancelBooking(10L, "alex"));

        verify(bookingRepository).findById(10L);

        verifyNoInteractions(paymentRepository, bookingDetailRepository,
                scheduleSeatRepository, refundService);
    }


    @Test
    void cancelBookingTravelDateReachedTest() {

        Booking booking = mock(Booking.class);
        Passenger passenger = mock(Passenger.class);
        User user = mock(User.class);
        Schedule schedule = mock(Schedule.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getPassenger()).thenReturn(passenger);
        when(passenger.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("alex");

        when(booking.getBookingStatus()).thenReturn(BookingStatus.CONFIRMED);
        when(booking.getSchedule()).thenReturn(schedule);

        when(schedule.getDepartureDatetime())
                .thenReturn(LocalDateTime.now().minusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.cancelBooking(10L, "alex"));

        verifyNoInteractions(paymentRepository, bookingDetailRepository,
                scheduleSeatRepository, refundService);
    }


    @Test
    void cancelBookingPaymentNotFoundTest() {

        Booking booking = mock(Booking.class);
        Passenger passenger = mock(Passenger.class);
        User user = mock(User.class);
        Schedule schedule = mock(Schedule.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getPassenger()).thenReturn(passenger);
        when(passenger.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("alex");

        when(booking.getBookingStatus()).thenReturn(BookingStatus.CONFIRMED);
        when(booking.getSchedule()).thenReturn(schedule);

        when(schedule.getDepartureDatetime())
                .thenReturn(LocalDateTime.now().plusDays(2));

        when(paymentRepository.findPaymentByBookingIdStatus(
                10L, PaymentStatus.SUCCESS))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.cancelBooking(10L, "alex"));

        verify(paymentRepository).findPaymentByBookingIdStatus(
                10L, PaymentStatus.SUCCESS);

        verifyNoInteractions(bookingDetailRepository,
                scheduleSeatRepository, refundService);
    }


    @Test
    void cancelBookingTest() {

        Booking booking = mock(Booking.class);
        Passenger passenger = mock(Passenger.class);
        User user = mock(User.class);
        Schedule schedule = mock(Schedule.class);
        Payment payment = mock(Payment.class);
        BookingDetail detail = mock(BookingDetail.class);
        ScheduleSeat seat = mock(ScheduleSeat.class);

        when(bookingRepository.findById(10L))
                .thenReturn(Optional.of(booking));

        when(booking.getPassenger()).thenReturn(passenger);
        when(passenger.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("alex");

        when(booking.getBookingStatus()).thenReturn(BookingStatus.CONFIRMED);
        when(booking.getSchedule()).thenReturn(schedule);

        when(schedule.getDepartureDatetime())
                .thenReturn(LocalDateTime.now().plusDays(2));

        when(paymentRepository.findPaymentByBookingIdStatus(
                10L, PaymentStatus.SUCCESS))
                .thenReturn(Optional.of(payment));

        when(payment.getId()).thenReturn(20L);

        when(bookingDetailRepository.findByBookingId(10L))
                .thenReturn(List.of(detail));

        when(detail.getScheduleSeat()).thenReturn(seat);

        bookingService.cancelBooking(10L, "alex");

        verify(refundService).insertRefund(20L);
        verify(bookingDetailRepository).findByBookingId(10L);

        verify(seat).setSeatStatus(SeatStatus.AVAILABLE);
        verify(scheduleSeatRepository).save(seat);

        verify(booking).setBookingStatus(BookingStatus.CANCELLED);
        verify(bookingRepository).save(booking);
    }
}
