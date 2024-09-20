package com.sparta.spartdelivery.domain.order.dto;

import com.sparta.spartdelivery.domain.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private final long menuId;
    private final long storeId;
    private final String status;

    public OrderResponseDto(Order order) {
        this.menuId = order.getMenuId();
        this.storeId = order.getStoreId();
        this.status = order.getStatus().toString();
    }
}
