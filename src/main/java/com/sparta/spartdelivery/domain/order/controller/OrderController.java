package com.sparta.spartdelivery.domain.order.controller;

import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 손님 - 주문하기
    @PostMapping("/orders")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> sendOrder(@RequestBody OrderRequestDto orderRequestDto) {

        Order order = orderService.sendOrder(orderRequestDto);

        OrderResponseDto orderResponseDto = new OrderResponseDto(order);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        HttpStatus.OK, "success", orderResponseDto
                ),
                HttpStatus.OK);
    }

    // 사장님 - 주문 내역 조회하기
    @GetMapping("/orders")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> getAllOrders() {
        return null;
    }

    // 사장님 - 주문 상태 변경하기
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> updateOrderAcceptStatus(@PathVariable Order order) {
        return null;
    }
}
