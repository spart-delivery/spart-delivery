package com.sparta.spartdelivery.domain.review.dto.responseDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewReadResponseDto {

    private Long reviewId;
    private Integer starPoint;
    private String comment;
    private LocalDateTime modifiedAt;

}
