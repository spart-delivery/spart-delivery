package com.sparta.spartdelivery.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreStatisticsRequestDto {

    @NotBlank(message = "시작날짜는 필수 파라미터입니다.")
    private String startDate;

    @NotBlank(message = "마지막날짜는 필수 파라미터입니다.")
    private String endDate;

    @NotBlank(message = "월간 또는 일간 타입을 명시해주세요.")
    private String dataType;
}
