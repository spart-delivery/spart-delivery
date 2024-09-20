package com.sparta.spartdelivery.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {

    private long storeId;
    private long userId;
    private long menuId;
}
