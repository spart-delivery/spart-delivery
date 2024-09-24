package com.sparta.spartdelivery.domain.review.service;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.entity.OrderStatus;
import com.sparta.spartdelivery.domain.order.repository.OrderRepository;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewEditRequestDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewEditResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewReadResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.entity.Review;
import com.sparta.spartdelivery.domain.review.exception.*;
import com.sparta.spartdelivery.domain.review.repository.ReviewRepository;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.exception.UserNotFoundException;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // 리뷰 작성
    public ReviewSaveResponseDto saveReview(AuthUser authUser, ReviewSaveRequestDto reviewSaveRequestDto, Long orderId) {

        // 유저확인
        validateUser(authUser);

        // 주문 확인절차
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문이 존재하지 않습니다."));


        // 이미 해당 주문이 존재하면 오류발생
        if (reviewRepository.existsByOrderId(orderId)) {
            throw new ExistReviewException();
        }

        // 주문 상태가 COMPLETED 인지 확인
        if (!order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new DoNotReviewUnCompletedOrderException();
        }

        // 별점 입력값 확인(1 ~ 5)
        validateStarPoint(reviewSaveRequestDto.getStarPoint());

        // 테이블 저장
        Review review = reviewRepository.save(new Review(reviewSaveRequestDto, authUser.getId(), orderId));

        return new ReviewSaveResponseDto(review);
    }

    // 리뷰 수정
    @Transactional
    public ReviewEditResponseDto editReview(AuthUser authUser, ReviewEditRequestDto reviewEditRequestDto, Long reviewId) {

        // 유저확인

        validateUser(authUser);

        // 유저 본인 작성 리뷰인지 확인 절차
        validateAuthReview(reviewId, authUser.getId());

        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundReviewException::new);

        // 별점 입력값 확인(1 ~ 5)
        validateStarPoint(reviewEditRequestDto.getStarPoint());

        // 수정내용 저장
        review.update(reviewEditRequestDto);
        reviewRepository.save(review);

        return new ReviewEditResponseDto(review);
    }

    // 가게 리뷰 전체 조회
    public List<ReviewReadResponseDto> readReview(Long storeId) {
        // 가게 확인절차
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);

        return reviews.stream()
                .map(review -> new ReviewReadResponseDto(
                        review.getReviewId(),
                        review.getStarPoint(),
                        review.getComment(),
                        review.getModifiedAt()
                )).toList();
    }

    // 리뷰 삭제
    public void deleteReview(AuthUser authUser, Long reviewId) {

        validateUser(authUser);

        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundReviewException::new);

        // 유저 본인 작성 리뷰인지 확인 절차
        validateAuthReview(reviewId, authUser.getId());

        reviewRepository.deleteById(reviewId);
    }

    // 유저 확인 메서드
    private void validateUser(AuthUser authUser) {
        Long userId = authUser.getId();

        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }
    }

    // 별점 입력값 확인 메서드
    private void validateStarPoint(Integer starPoint) {
        if(starPoint < 1 || starPoint > 5) {
            throw new ValidatationStarPointException();
        }
    }

    // 유저 본인이 작성한 리뷰인지 검증하는 메서드
    private void validateAuthReview(Long reviewId, Long userId) {
        if (!reviewRepository.existsByReviewIdAndUserId(reviewId, userId)) {
            throw new DoNotDeleteReviewException();
        }
    }

}
