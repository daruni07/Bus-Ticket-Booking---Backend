package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.BookingReqDto;
import com.springboot.busticketbooking.dto.response.BookingRespDto;
import com.springboot.busticketbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping("/api/booking/add")
    public void createBooking(
            @Valid @RequestBody BookingReqDto bookingReqDto, Principal principal) {

        bookingService.createBooking(bookingReqDto, principal.getName());
    }
    @GetMapping("/api/booking/history")
    public List<BookingRespDto> getBookingHistory(Principal principal)
    {
        return bookingService.getBookingHistory(principal.getName());
    }
    @GetMapping("/api/booking/{bookingId}")
    public  BookingRespDto getBookingById(@PathVariable Long bookingId)
    {
        return bookingService.getBookingById(bookingId);
    }
    @PutMapping("/api/bookings/cancel/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId,Principal principal)
    {
        bookingService.cancelBooking(bookingId, principal.getName());
    }
    @GetMapping("/operator")
    public List<BookingRespDto> getOperatorBookings(Principal principal) {

        return bookingService.getOperatorBookings(principal.getName());
    }

}
