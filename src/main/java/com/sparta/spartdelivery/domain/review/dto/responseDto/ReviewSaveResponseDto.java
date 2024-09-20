package com.sparta.spartdelivery.domain.review.dto.responseDto;

import com.sparta.spartdelivery.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewSaveResponseDto {

    private Long reviewId;
    private Integer starPoint;
    private String content;
    private LocalDateTime createdAt;

    public ReviewSaveResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.starPoint = review.getStarPoint();
        this.content = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}
