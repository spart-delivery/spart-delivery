package com.sparta.spartdelivery.domain.review.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReadResponseDto {

    private Long reviewId;
    private Integer starPoint;
    private String comment;
    private LocalDateTime modifiedAt;

}
