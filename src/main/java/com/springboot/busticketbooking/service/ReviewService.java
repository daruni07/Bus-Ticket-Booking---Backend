package com.springboot.busticketbooking.service;

import com.springboot.busticketbooking.dto.request.ReviewReqDto;
import com.springboot.busticketbooking.dto.response.ReviewRespDto;
import com.springboot.busticketbooking.enums.BookingStatus;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import com.springboot.busticketbooking.mapper.ReviewMapper;
import com.springboot.busticketbooking.model.Booking;
import com.springboot.busticketbooking.model.Passenger;
import com.springboot.busticketbooking.model.Review;
import com.springboot.busticketbooking.repository.BookingRepository;
import com.springboot.busticketbooking.repository.PassengerRepository;
import com.springboot.busticketbooking.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    @Transactional
    public void addReview(ReviewReqDto reviewReqDto, String name) {

        Booking booking=bookingRepository.findById(reviewReqDto.bookingID())
                .orElseThrow(()->new ResourceNotFoundException("Invalid booking id"));
        if (!booking.getPassenger().getUser().getUsername().equals(name)) {
            throw new OwnershipException(
                    "You cannot review another passenger's booking");
        }
        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Only confirmed bookings can be reviewed");
        }
        if (!booking.getSchedule().getDepartureDatetime()
                .isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "You can review only after the travel departure time");
        }
        Review review=new Review();
        review.setBooking(booking);
        review.setRating(reviewReqDto.rating());
        review.setComment(reviewReqDto.comment());
        review.setReviewDate(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public List<ReviewRespDto> getReviewsByBus(Long busId) {
        return reviewRepository
                .findReviewByBusId(busId)
                .stream()
                .map(ReviewMapper::mapToDto)
                .toList();
    }

    public List<ReviewRespDto> getMyReviews(String name) {
        return reviewRepository
                .findReviewByUsername(name)
                .stream()
                .map(ReviewMapper::mapToDto)
                .toList();
    }
}
