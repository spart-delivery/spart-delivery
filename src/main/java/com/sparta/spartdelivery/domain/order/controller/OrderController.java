package com.sparta.spartdelivery.domain.order.controller;

import com.sparta.spartdelivery.common.annotation.Auth;
import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.dto.response.CommonResponseDto;
import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> sendOrder(
            @Auth AuthUser authUser,
            @RequestBody OrderRequestDto orderRequestDto) throws IllegalAccessException {

        Order order = orderService.sendOrder(authUser, orderRequestDto);

        OrderResponseDto orderResponseDto = new OrderResponseDto(order);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        HttpStatus.OK, "success", orderResponseDto
                ),
                HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<CommonResponseDto<List<OrderResponseDto>>> getAllOrders(@Auth AuthUser authUser) throws IllegalAccessException {
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders(authUser);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        HttpStatus.OK, "success", orderResponseDtos
                ),
                HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> updateOrderStatus(
            @Auth AuthUser authUser,
            @PathVariable long orderId,
            @RequestBody OrderRequestDto orderRequestDto)
            throws IllegalAccessException {

        Order order = orderService.updateOrderStatus(authUser, orderId, orderRequestDto);

        OrderResponseDto orderResponseDto = new OrderResponseDto(order);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        HttpStatus.OK, "success", orderResponseDto
                ),
                HttpStatus.OK);
    }
}
