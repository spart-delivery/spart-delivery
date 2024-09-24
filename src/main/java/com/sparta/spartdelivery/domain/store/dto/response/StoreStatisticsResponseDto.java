package com.sparta.spartdelivery.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class StoreStatisticsResponseDto {

    private String orderDate;

    private Long guestCount;

    private BigDecimal sales;

}
