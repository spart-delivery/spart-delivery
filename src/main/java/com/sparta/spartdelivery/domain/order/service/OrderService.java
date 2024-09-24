package com.sparta.spartdelivery.domain.order.service;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.entity.OrderStatus;
import com.sparta.spartdelivery.domain.order.repository.OrderRepository;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    /**
     * 주문하기 ( 유저 구분 x )
     * @param authUser 로그인한 사용자
     * @param orderRequestDto 사용자의 입력 데이터 DTO
     * @return 새 주문
     * @throws IllegalAccessException 부적절한 접근 예외 처리
     */
    @Transactional
    public Order sendOrder(AuthUser authUser, OrderRequestDto orderRequestDto) throws IllegalAccessException {

        User user = findByNullableId(userRepository, authUser.getId(), "존재하지 않는 사용자입니다.");
        Menu menu = findByNullableId(menuRepository, orderRequestDto.getMenuId(), "존재하지 않는 메뉴입니다.");
        Store store = findByNullableId(storeRepository, orderRequestDto.getStoreId(), "존재하지 않는 가게입니다.");

        if (menu.getMenuPrice() < store.getMinOrderPrice()) {
            throw new IllegalAccessException("최소 주문 금액 이상 주문해야 합니다.");
        }

        // 다른 가게 메뉴를 주문하는지 체크 -> 장바구니 구현 시 throw 대신에  ~~~~~
        if (!Objects.equals(store.getStoreId(), menu.getStoreId())) {
            throw new IllegalAccessException("해당 가게의 메뉴만 주문할 수 있습니다.");
        }

        // 오픈시간 이전, 마감시간 이후에는 주문 불가 처리
        LocalTime now = LocalDateTime.now().toLocalTime();
        LocalTime openTime = store.getOpenTime();
        LocalTime closeTime = store.getCloseTime();

        if (now.isBefore(openTime) || now.isAfter(closeTime)) {
            throw new IllegalAccessException("영업시간 이외의 주문은 불가합니다.");
        }

        Order newOrder = new Order(user.getUserId(), store.getStoreId(), menu.getMenuId());

        return orderRepository.save(newOrder);
    }

    /**
     * 들어온 주문 내역 조회하기 ( 사장님만 해당 )
     * @param authUser 로그인한 사용자
     * @return 주문 내역 DTO 리스트
     * @throws IllegalAccessException 부적절한 접근 예외 처리
     */
    public List<OrderResponseDto> getAllOrders(AuthUser authUser) throws IllegalAccessException {

        User user = findByNullableId(userRepository, authUser.getId(), "존재하지 않는 사용자입니다.");
        ownerCheck(user);
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new NullPointerException("들어온 주문 내역이 없습니다.");
        }

        // OrderResponseDto 타입으로 변환한 컬렉션 생성
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for (Order order : orders) {
            orderResponseDtos.add(new OrderResponseDto(order));
        }
        return orderResponseDtos;
    }

    /**
     * 주문 상태 변경하기 ( 사장님만 해당 )
     * @param authUser 로그인한 사용자
     * @param orderId 주문 아이디
     * @param orderRequestDto 사용자의 입력 데이터 DTO
     * @return 변경된 주문
     * @throws IllegalAccessException 부적절한 접근 예외 처리
     */
    @Transactional
    public Order updateOrderStatus(AuthUser authUser, long orderId, OrderRequestDto orderRequestDto) throws IllegalAccessException {
        User user = findByNullableId(userRepository, authUser.getId(), "존재하지 않는 사용자입니다.");
        Order order = findByNullableId(orderRepository, orderId, "존재하지 않는 주문입니다.");

        ownerCheck(user);

        if (orderRequestDto.getStatus() == null) {
            throw new IllegalAccessException("변경할 상태를 입력해 주세요.");
        }

        // 주문 상태를 '주문 수락' 으로 변경할 때 / 주문 상태를 '배달 완료' 로 변경할 때
        if (order.getStatus().equals(OrderStatus.ORDERED)) {
            order.acceptedOrder();
        } else if (order.getStatus().equals(OrderStatus.ACCEPTED)) {
            order.completedOrder();
        }

        orderRepository.save(order);
        return order;
    }

    public <T> T findByNullableId(JpaRepository<T, Long> repository, Long id, String errorMsg) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NullPointerException(errorMsg));
    }

    public void ownerCheck(User user) throws IllegalAccessException {
        if (user.getUserRole() != UserRole.OWNER) {
            throw new IllegalAccessException("가게의 사장님만 접근할 수 있습니다.");
        }
    }
}
