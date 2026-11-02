package com.springboot.busticketbooking.controller;

import com.springboot.busticketbooking.dto.request.ReviewReqDto;
import com.springboot.busticketbooking.dto.response.ReviewRespDto;
import com.springboot.busticketbooking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/api/review/add")
    public void addReview(@RequestBody ReviewReqDto dto, Principal principal)
    {
                reviewService.addReview(dto, principal.getName());
    }

    @GetMapping("/api/review/bus/{busId}")
    public List<ReviewRespDto> getReviewsByBus(@PathVariable Long busId)
    {
        return reviewService.getReviewsByBus(busId);
    }

    @GetMapping("/api/review/user")
    public List<ReviewRespDto> getMyReviews(Principal principal) {

        return reviewService.getMyReviews(principal.getName());
    }
}
