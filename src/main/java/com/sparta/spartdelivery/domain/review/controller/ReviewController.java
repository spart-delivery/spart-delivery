package com.sparta.spartdelivery.domain.review.controller;

import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewEditRequestDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewEditResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping()
    public ResponseEntity<ReviewSaveResponseDto> saveReview(@RequestBody ReviewSaveRequestDto reviewSaveRequestDto/*,
                                                            @PathVariable Long orderId*/) {
        ReviewSaveResponseDto reviewSaveResponseDto = reviewService.saveReview(reviewSaveRequestDto/*,orderId*/);
        return new ResponseEntity<>(reviewSaveResponseDto, HttpStatus.CREATED);
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewEditResponseDto> editReview(@RequestBody ReviewEditRequestDto reviewEditRequestDto,
                                                            @PathVariable Long reviewId) {
        ReviewEditResponseDto reviewEditResponseDto = reviewService.editReview(reviewEditRequestDto, reviewId);
        return new ResponseEntity<>(reviewEditResponseDto, HttpStatus.OK);
    }

    // 리뷰 조회
//    @GetMapping("/{storeId}")
//    public ResponseEntity<List<ReviewReadResponseDto>> readReview(@PathVariable Long storeId) {
//        List<ReviewReadResponseDto> reviewReadResponseDto = reviewService.readReview(storeId);
//        return ResponseEntity<>(reviewReadResponseDto, HttpStatus.OK);
//    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
