package com.sparta.spartdelivery.domain.order.service;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.repository.OrderRepository;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    // 주문하기
    @Transactional
    public Order sendOrder(AuthUser authUser, OrderRequestDto orderRequestDto) { // AuthUser 형식에서 추출해야 되는지?

        // 현재 사용자의 id가 존재하는지 체크
        User user = findByNullableId(userRepository, authUser.getId(), "존재하지 않는 사용자입니다.");

        // 주문할 메뉴의 id가 존재하는지 체크
        Menu menu = findByNullableId(menuRepository, orderRequestDto.getMenuId(), "존재하지 않는 메뉴입니다.");

        // 주문할 가게의 id가 존재하는지 체크
        Store store = findByNullableId(storeRepository, orderRequestDto.getStoreId(), "존재하지 않는 가게입니다.");

        // 주문 시 체크할 또 다른 예외


        // 찾은 아이디들을 저장한 새 주문 생성
        Order newOrder = new Order(user.getUserId(), store.getStoreId(), menu.getMenuId());

        // 주문 레포지토리에 저장
        Order sendOrder = orderRepository.save(newOrder);

        return new Order(sendOrder);
    }

    // 들어온 주문 내역 조회하기
    public List<OrderResponseDto> getAllOrders() {

        // 예외 처리

        // Order 타입의 orders 리스트에 레포지토리에서 찾은 모든 주문 내역 조회
        List<Order> orders = orderRepository.findAll();

        // OrderResponseDto 타입으로 변환한 컬렉션 생성
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for (Order order : orders) {
            orderResponseDtos.add(new OrderResponseDto(order));
        }
        return orderResponseDtos;
    }


//    // 주문 상태 변경하기
//    public Order updateOrderStatus(AuthUser authUser, long orderId, OrderRequestDto orderRequestDto) { // AuthUser에서 추출해야 하는지
//
//        // 현재 사용자의 id가 존재하는지 체크
//        User user = findByNullableId(userRepository, authUser.getId(), "존재하지 않는 사용자입니다.");
//
//        // 주문 내역에 있는 주문이 맞는지 체크
//        Order order = findByNullableId(orderRepository, orderId, "존재하지 않는 주문입니다.");
//
//        // 현재 사용자의 role이 OWNER인지 체크
//        if (user.getUserRole() == UserRole.OWNER) {
//
//        }
//
//
//        // 주문 상태를 '주문 수락' 으로 변경할 때
//        if (orderRequestDto.getStatus() == Order.Status.ORDERED) {
//            acceptedOrder();
//        }
//
//        // 주문 상태를 '배달 완료' 로 변경할 때
//
//
//        return null;
//    }

    // null일 수 있는 id가 존재하는지 찾기
    public <T> T findByNullableId(JpaRepository<T, Long> repository, Long id, String errorMsg) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NullPointerException(errorMsg));
    }
}
