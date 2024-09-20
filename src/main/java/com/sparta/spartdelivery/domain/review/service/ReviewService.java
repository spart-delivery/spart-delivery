package com.sparta.spartdelivery.domain.review.service;

import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewEditRequestDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewEditResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewReadResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.entity.Review;
import com.sparta.spartdelivery.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

//    private final OrderRepository orderRepository;

    // 리뷰 작성
    public ReviewSaveResponseDto saveReview(ReviewSaveRequestDto reviewSaveRequestDto/*, Long orderId*/) {
//        // 주문 확인절차
//        Menu menu = orderRepository.findById(orderId).orElseThrow(() ->
//                new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
        // 별점 입력값 확인
        validateStarPoint(reviewSaveRequestDto.getStarPoint());

        // 테이블 저장
        Review review = reviewRepository.save(new Review(reviewSaveRequestDto));

        return new ReviewSaveResponseDto(review);
    }

    // 리뷰 수정
    public ReviewEditResponseDto editReview(ReviewEditRequestDto reviewEditRequestDto, Long reviewId) {
        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰는 없습니다."));

        // 별점 입력값 확인
        validateStarPoint(reviewEditRequestDto.getStarPoint());

        // 유저 본인 작성 리뷰확인

        // 수정내용 저장
        review.update(reviewEditRequestDto);
        reviewRepository.save(review);

        return new ReviewEditResponseDto(review);
    }

    // 가게 리뷰 전체 조회
    public List<ReviewReadResponseDto> readReview(Long storeId) {
//        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);
        return null;
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰는 없습니다."));

        reviewRepository.deleteById(reviewId);
    }

    // 별점 입력값 확인 메서드
    private void validateStarPoint(Integer starPoint) {
        if(starPoint < 0 || starPoint > 6) {
            throw new IllegalArgumentException("별점은 1 ~ 5 만 입력값만 입력할 수 있습니다.");
        }
    }

}
