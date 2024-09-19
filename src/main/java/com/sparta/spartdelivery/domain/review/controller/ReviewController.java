package com.sparta.spartdelivery.domain.review.controller;

import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/save/{menuId}")
    public ResponseEntity<ReviewSaveResponseDto> saveReview(@RequestBody ReviewSaveRequestDto reviewSaveRequestDto/*,
                                                            @PathVariable Long orderId*/) {
        ReviewSaveResponseDto reviewSaveResponseDto = reviewService.saveReview(reviewSaveRequestDto/*,orderId*/);
        return new ResponseEntity<>(reviewSaveResponseDto, HttpStatus.CREATED);
    }
}
