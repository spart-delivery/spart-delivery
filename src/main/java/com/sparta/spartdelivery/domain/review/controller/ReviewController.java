package com.sparta.spartdelivery.domain.review.controller;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewEditRequestDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewEditResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewReadResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/reviews/{orderId}")
    public ResponseEntity<CommonResponseDto<ReviewSaveResponseDto>> saveReview(@Auth AuthUser authUser,
                                                                               @RequestBody ReviewSaveRequestDto reviewSaveRequestDto,
                                                                               @PathVariable Long orderId) {
        ReviewSaveResponseDto reviewSaveResponseDto = reviewService.saveReview(authUser, reviewSaveRequestDto, orderId);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.CREATED, "success", reviewSaveResponseDto), HttpStatus.CREATED);
    }

    // 리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponseDto<ReviewEditResponseDto>> editReview(@Auth AuthUser authUser,
                                                                               @RequestBody ReviewEditRequestDto reviewEditRequestDto,
                                                                               @PathVariable Long reviewId) {
        ReviewEditResponseDto reviewEditResponseDto = reviewService.editReview(authUser, reviewEditRequestDto, reviewId);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", reviewEditResponseDto), HttpStatus.OK);
    }

    // 리뷰 조회
    @GetMapping("/reviews/stores/{storeId}")
    public ResponseEntity<CommonResponseDto<List<ReviewReadResponseDto>>> readReview(@PathVariable Long storeId) {
        List<ReviewReadResponseDto> reviewReadResponseDto = reviewService.readReview(storeId);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, "success", reviewReadResponseDto), HttpStatus.OK);
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteReview(@Auth AuthUser authUser,
                                                                @PathVariable Long reviewId) {
        reviewService.deleteReview(authUser, reviewId);
        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.NO_CONTENT, "success", null), HttpStatus.NO_CONTENT);
    }

}
