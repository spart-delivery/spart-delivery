package com.sparta.spartdelivery.domain.review.serviceTest;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.repository.OrderRepository;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewEditRequestDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewEditResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewReadResponseDto;
import com.sparta.spartdelivery.domain.review.dto.responseDto.ReviewSaveResponseDto;
import com.sparta.spartdelivery.domain.review.entity.Review;
import com.sparta.spartdelivery.domain.review.repository.ReviewRepository;
import com.sparta.spartdelivery.domain.review.service.ReviewService;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    StoreRepository storeRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewService reviewService;


    private AuthUser authUser;
    private Order order;
    private Review review;
    private ReviewSaveRequestDto reviewSaveRequestDto;
    private ReviewEditRequestDto reviewEditRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewSaveRequestDto = new ReviewSaveRequestDto(1L, 5, "잘 먹었습니다!");
        reviewEditRequestDto = new ReviewEditRequestDto(5, "수정");
        authUser = new AuthUser(1L, "testUser", UserRole.USER);
        review = new Review();
        order = new Order();
        order.completedOrder();
    }

    @Test
    void 리뷰저장_성공 () {
        // given
        Long orderId = 1L;
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(mock(User.class)));
        when(reviewRepository.existsByOrderId(orderId)).thenReturn(false);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(reviewRepository.save(any(Review.class))).thenReturn(mock(Review.class));

        // when
        ReviewSaveResponseDto response = reviewService.saveReview(authUser, reviewSaveRequestDto, orderId);

        // then
        assertNotNull(response);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void 리뷰저장_실패_주문_못찾음() {
        // given
        Long orderId = 1L;
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(mock(User.class)));
        when(reviewRepository.existsByOrderId(orderId)).thenReturn(false);
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.saveReview(authUser, reviewSaveRequestDto, orderId));
        assertEquals("해당 주문이 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    void 리뷰저장_실패_유저_못찾음 () {
        // given
        Long orderId = 1L;
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.empty());

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.saveReview(authUser, reviewSaveRequestDto, orderId));
        assertEquals("해당 유저는 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    void 리뷰저장_실패_이미_리뷰있음 () {
        // given
        Long orderId = 1L;
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(mock(User.class)));
        when(reviewRepository.existsByOrderId(orderId)).thenReturn(true);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.saveReview(authUser, reviewSaveRequestDto, orderId));
        assertEquals("해당 주문에 대한 리뷰가 이미 존재합니다.", exception.getMessage());
    }

    @Test
    void 리뷰저장_실패_배달완료상태아님 () {
        // given
        Long orderId = 1L;
        order.acceptedOrder();
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(mock(User.class)));
        when(reviewRepository.existsByOrderId(orderId)).thenReturn(false);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.saveReview(authUser, reviewSaveRequestDto, orderId));
        assertEquals("리뷰는 완료된 주문에 한해서만 작성할 수 있습니다.",exception.getMessage());
    }

    @Test
    void 리뷰수정_성공 () {
        // given
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(mock(User.class)));
        when(reviewRepository.findById(review.getReviewId())).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(mock(Review.class));
        when(reviewRepository.existsByReviewIdAndUserId(review.getReviewId(), authUser.getId())).thenReturn(true);

        // when
        ReviewEditResponseDto responseDto = reviewService.editReview(authUser, reviewEditRequestDto, review.getReviewId());

        // then
        assertNotNull(responseDto);
        assertEquals(reviewEditRequestDto.getStarPoint(), responseDto.getStarPoint());
        assertEquals(reviewEditRequestDto.getComment(), responseDto.getContent());
        verify(reviewRepository).save(review); // 리뷰 저장 호출 여부 확인
    }

    @Test
    void 리뷰수정_실패_리뷰_못찾음 () {
        // given
        Long reviewId = 1L;
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(mock(User.class)));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
        when(reviewRepository.existsByReviewIdAndUserId(review.getReviewId(), authUser.getId())).thenReturn(true);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reviewService.editReview(authUser, reviewEditRequestDto, review.getReviewId()));

        assertEquals("해당 리뷰는 없습니다.", exception.getMessage());
    }

    @Test
    void 가게리뷰조회_성공() {
        // Given
        Long storeId = 1L;
        List<Review> mockReviews = List.of(
                new Review(new ReviewSaveRequestDto(1L, 5, "잘 먹었습니다."), 1L, 1L),
                new Review(new ReviewSaveRequestDto(1L, 2, "감사합니다."), 2L, 2L)
        );

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(new Store()));
        when(reviewRepository.findAllByStoreId(storeId)).thenReturn(mockReviews);

        // When
        List<ReviewReadResponseDto> result = reviewService.readReview(storeId);

        // Then
        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getStarPoint());
        assertEquals("잘 먹었습니다.", result.get(0).getComment());
        assertEquals(2, result.get(1).getStarPoint());
        assertEquals("감사합니다.", result.get(1).getComment());
    }
}