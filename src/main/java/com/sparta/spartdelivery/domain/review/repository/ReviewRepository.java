package com.sparta.spartdelivery.domain.review.repository;

import com.sparta.spartdelivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByStoreId(Long storeId);

    boolean existsByOrderId(Long orderId);

    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
}
