package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.response.BookingDetailRespDto;
import com.springboot.busticketbooking.dto.response.BookingRespDto;
import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.model.Booking;
import com.springboot.busticketbooking.model.BookingDetail;

import java.util.List;

public class BookingMapper {
    public static BookingRespDto mapToResponse(Booking booking, List<BookingDetail> details, PaymentStatus paymentStatus)
    {

            List<BookingDetailRespDto> passengers = details.stream()
                    .map(detail -> new BookingDetailRespDto(
                            detail.getName(),
                            detail.getAge(),
                            detail.getGender(),
                            detail.getGovtIdType(),
                            detail.getScheduleSeat().getSeat().getSeatNumber(),
                            detail.getBoardingRouteStop().getStop().getStopName(),
                            detail.getDroppingRouteStop().getStop().getStopName()
                    ))
                    .toList();

            return new BookingRespDto(
                    booking.getId(),
                    booking.getBookingDate(),
                    booking.getBookingStatus(),
                    booking.getTotalAmount(),
                    booking.getSchedule().getId(),
                    paymentStatus,
                    passengers
            );
        }
}
