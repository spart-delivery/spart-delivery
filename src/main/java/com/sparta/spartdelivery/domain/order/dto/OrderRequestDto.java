package com.sparta.spartdelivery.domain.order.dto;

import com.sparta.spartdelivery.domain.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    private long storeId;
    private long menuId;
    private Order.Status status;
}