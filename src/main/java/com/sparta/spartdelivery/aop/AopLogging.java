package com.sparta.spartdelivery.aop;

import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j(topic = "AopLogging")
@Aspect
@Component
@RequiredArgsConstructor
public class AopLogging {

    @Pointcut("execution(* com.sparta.spartdelivery.domain.order.service.OrderService.sendOrder(..))")
    public void sendOrder() {}

    @Pointcut("execution(* com.sparta.spartdelivery.domain.order.service.OrderService.updateOrderStatus(..))")
    public void updateOrderStatus() {}

    @AfterReturning(pointcut = "sendOrder()", returning = "order")
    public void afterSendOrderApi(JoinPoint joinPoint, Order order) {

        LocalDateTime requestTime = LocalDateTime.now(); // 요청 시각
        OrderRequestDto orderRequestDto = (OrderRequestDto) joinPoint.getArgs()[1]; // dto 가져오기
        Long requestStoreId = orderRequestDto.getStoreId(); // 가게 id

        if (order != null) {
            Long requestOrderId = order.getOrderId();
            log.info("API 요청 시각 : {}, 가게 id : {}, 주문 id : {}", requestTime, requestStoreId, requestOrderId);
        } else {
            log.warn("주문 id가 null 입니다.");
        }
    }


    @After("updateOrderStatus()")
    public void afterUpdateOrderStatusApi(JoinPoint joinPoint) {

        LocalDateTime requestTime = LocalDateTime.now(); // 요청 시각
        OrderRequestDto orderRequestDto = (OrderRequestDto) joinPoint.getArgs()[2]; // dto 가져오기
        Long requestStoreId = orderRequestDto.getStoreId(); // 가게 id
        OrderStatus requestStatus = orderRequestDto.getStatus(); // 주문 상태

        Long requestOrderId = (long) joinPoint.getArgs()[1];
        log.info("API 요청 시각 : {}, 가게 id : {}, 주문 id : {}, 변경된 주문 상태 : {}", requestTime, requestStoreId, requestOrderId, requestStatus);
    }
}
