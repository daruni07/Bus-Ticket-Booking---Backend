package com.springboot.busticketbooking.service;


import com.springboot.busticketbooking.dto.request.BookingReqDto;
import com.springboot.busticketbooking.dto.request.TravellerReqDto;
import com.springboot.busticketbooking.dto.response.BookingRespDto;
import com.springboot.busticketbooking.enums.BookingStatus;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.SeatStatus;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.BookingMapper;
import com.springboot.busticketbooking.model.*;
import com.springboot.busticketbooking.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleSeatRepository scheduleSeatRepository;
    private final RouteStopRepository routeStopRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final ScheduleFareService scheduleFareService;
    private final PaymentRepository paymentRepository;
    private final RefundService refundService;
    @Transactional
    public void createBooking(BookingReqDto bookingReqDto, String name) {
        Optional<Passenger> passengerOptional=passengerRepository.findByUsername(name);
        List<BookingDetail> bookingList = new ArrayList<>();
        if(passengerOptional.isEmpty())
        {
            throw  new ResourceNotFoundException("Invalid passenger username");
        }
        Passenger passenger=passengerOptional.get();
        Schedule schedule = scheduleRepository.findById(bookingReqDto.scheduleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Schedule not found"));
        double totalAmount = 0;
        for (TravellerReqDto traveller : bookingReqDto.passengers()) {

            ScheduleSeat scheduleSeat = scheduleSeatRepository
                    .findById(traveller.scheduleSeatId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Seat not found"));

            if (scheduleSeat.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalArgumentException("Seat is not available");
            }

        RouteStop boardingStop = routeStopRepository
                .findById(traveller.boardingRouteStopId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Boarding stop not found"));

        RouteStop droppingStop = routeStopRepository
                .findById(traveller.droppingRouteStopId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dropping stop not found"));
            Seat seat = scheduleSeat.getSeat();

            ScheduleFare scheduleFare = scheduleFareService
                    .getScheduleFare(
                            schedule.getId(),
                            boardingStop.getId(),
                            droppingStop.getId(),
                            seat.getSeatType(),
                            seat.getSeatPosition(),
                            seat.getBerthLevel()
                    );

            double fare = scheduleFare.getFare();

            totalAmount += fare;


            BookingDetail detail = new BookingDetail();
            detail.setFare(fare);
            detail.setName(traveller.name());
            detail.setAge(traveller.age());
            detail.setGender(traveller.gender());
            detail.setGovtIdType(traveller.govtIdType());
            detail.setGovtIdProof(traveller.govtIdProof());
            detail.setScheduleSeat(scheduleSeat);
            detail.setBoardingRouteStop(boardingStop);
            detail.setDroppingRouteStop(droppingStop);
            bookingList.add(detail);
            scheduleSeat.setSeatStatus(SeatStatus.LOCKED);
            scheduleSeatRepository.save(scheduleSeat);

        }
        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setSchedule(schedule);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTotalAmount(totalAmount);

        booking = bookingRepository.save(booking);

        for (BookingDetail detail : bookingList) {
            detail.setBooking(booking);
        }

        bookingDetailRepository.saveAll(bookingList);
    }

    public List<BookingRespDto> getBookingHistory(String username) {
        Passenger passenger = passengerRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Passenger not found"));

        List<Booking> bookingList =bookingRepository.findByPassengerBooking(passenger.getId());
        return bookingList.stream()
                .map(this::getBookingResponse)
                .toList();
    }
    public BookingRespDto getBookingById(Long bookingId)
    {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Booking id invalid"));
        return getBookingResponse(booking);
    }
    private BookingRespDto getBookingResponse(Booking booking) {
        List<BookingDetail> details =
                bookingDetailRepository.findByBookingId(booking.getId());

        Optional<Payment> paymentOptional=paymentRepository.findPaymentByBookingId(booking.getId());
        if(paymentOptional.isEmpty())
        {
            throw  new ResourceNotFoundException("Payment for the given booking id is not found");
        }
        PaymentStatus paymentStatus = paymentOptional.get().getPaymentStatus();

        return BookingMapper.mapToResponse(
                booking, details, paymentStatus);
    }

    public void cancelBooking(Long bookingId,String username) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));
        if (!booking.getPassenger().getUser().getUsername()
                .equals(username)) {
            throw new OwnershipException(
                    "The booking doesn't belong to the logged in user");
        }
        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Only confirmed bookings can be cancelled");
        }
        LocalDate travelDate = booking.getSchedule()
                .getDepartureDatetime().toLocalDate();

        if (!travelDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Booking can only be cancelled before the travel date");
        }

        Payment payment = paymentRepository
                .findPaymentByBookingIdStatus(
                        bookingId, PaymentStatus.SUCCESS)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No successful payment found"));

        refundService.insertRefund(payment.getId());
        List<BookingDetail> details =
                bookingDetailRepository.findByBookingId(bookingId);
        for (BookingDetail detail : details) {
            ScheduleSeat scheduleSeat = detail.getScheduleSeat();
            scheduleSeat.setSeatStatus(SeatStatus.AVAILABLE);
            scheduleSeatRepository.save(scheduleSeat);
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
