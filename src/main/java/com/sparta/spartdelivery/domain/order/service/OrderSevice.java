/*
package com.sparta.spartdelivery.domain.order.service;

import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    // null일 수 있는 id가 존재하는지 찾기
    public <T> T findByNullableId(JpaRepository<T, Long> repository, Long id, String errorMsg) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NullPointerException(errorMsg));
    }

    // 주문하기
    @Transactional
    public Order sendOrder(OrderRequestDto orderRequestDto) {

        // 현재 사용자의 id가 존재하는지 체크
        User user = findByNullableId(userRepository, orderRequestDto.getUserId(), "존재하지 않는 사용자입니다.");

        // 주문할 가게의 id가 존재하는지 체크
        Store store = findByNullableId(storeRepository, orderRequestDto.getStoreId(), "존재하지 않는 가게입니다.");

        // 주문할 메뉴의 id가 존재하는지 체크
        Menu menu = findByNullableId(menuRepository, orderRequestDto.getMenuId(), "존재하지 않는 메뉴입니다.");

        // 주문 시 체크할 또 다른 예외


        // 찾은 아이디들을 저장한 새 주문 생성
        Order newOrder = new Order(user.getUserId(), store.getStoreId(), menu.getMenuId());

        // 주문 레포지토리에 저장
        Order sendOrder = orderRepository.save(newOrder);

        return new Order(sendOrder);
    }

    // 들어온 주문 내역 조회하기



    // 주문 수락으로 변경하기



    // 배달 완료로 변경하기

}
*/
