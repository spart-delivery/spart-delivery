package com.sparta.spartdelivery.domain.review.dto.requestDto;

import lombok.Getter;

@Getter
public class ReviewSaveRequestDto {

    private Long storeId;
    private Integer starPoint;
    private String comment;

}
