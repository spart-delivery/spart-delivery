package com.sparta.spartdelivery.domain.review.repository;

import com.sparta.spartdelivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
