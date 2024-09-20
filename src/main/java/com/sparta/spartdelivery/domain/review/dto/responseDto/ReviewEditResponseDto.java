package com.sparta.spartdelivery.domain.review.dto.responseDto;

import com.sparta.spartdelivery.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewEditResponseDto {

    private Long reviewId;
    private Integer starPoint;
    private String content;
    private LocalDateTime modifiedAt;

    public ReviewEditResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.starPoint = review.getStarPoint();
        this.content = review.getComment();
        this.modifiedAt = review.getModifiedAt();
    }


}
