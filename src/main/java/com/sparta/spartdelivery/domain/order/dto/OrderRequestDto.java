package com.sparta.spartdelivery.domain.order.dto;

import com.sparta.spartdelivery.domain.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    private long storeId;
    private long menuId;

    // 사장님만 입력
    private OrderStatus status;
}