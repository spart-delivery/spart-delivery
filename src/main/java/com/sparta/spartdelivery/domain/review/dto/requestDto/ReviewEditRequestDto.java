package com.sparta.spartdelivery.domain.review.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEditRequestDto {

    private Integer starPoint;
    private String comment;

}
