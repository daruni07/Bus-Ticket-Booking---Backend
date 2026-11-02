package com.springboot.busticketbooking.dto.response;

import java.time.LocalDateTime;

public record ReviewRespDto(Long reviewId,
                            Long bookingId,
                            Long busId,
                            double rating,
                            String comment,
                            LocalDateTime reviewDate) {
}
