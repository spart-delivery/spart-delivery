package com.sparta.spartdelivery.domain.review.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveRequestDto {

    private Long storeId;
    private Integer starPoint;
    private String comment;

}
