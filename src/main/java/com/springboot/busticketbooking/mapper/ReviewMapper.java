package com.springboot.busticketbooking.mapper;

import com.springboot.busticketbooking.dto.response.ReviewRespDto;
import com.springboot.busticketbooking.model.Review;

public class ReviewMapper {
    public static ReviewRespDto mapToDto(Review review) {
        return new ReviewRespDto(
                review.getId(),
                review.getBooking().getId(),
                review.getBooking().getSchedule().getBus().getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}
