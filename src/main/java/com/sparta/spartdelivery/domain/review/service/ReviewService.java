package com.sparta.spartdelivery.domain.review.service;

import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.entity.Review;
import com.sparta.spartdelivery.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

//    private final OrderRepository orderRepository;

    // 리뷰 저장
    public ReviewSaveResponseDto saveReview(ReviewSaveRequestDto reviewSaveRequestDto/*, Long orderId*/) {
//        // 주문 확인절차
//        Menu menu = orderRepository.findById(orderId).orElseThrow(() ->
//                new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        // 테이블 저장
        Review review = reviewRepository.save(new Review(reviewSaveRequestDto));

        return new ReviewSaveResponseDto(review);
    }
}
