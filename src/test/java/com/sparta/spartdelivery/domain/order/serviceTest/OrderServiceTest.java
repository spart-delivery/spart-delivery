package com.sparta.spartdelivery.domain.order.serviceTest;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import com.sparta.spartdelivery.domain.menu.repository.MenuRepository;
import com.sparta.spartdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.spartdelivery.domain.order.entity.Order;
import com.sparta.spartdelivery.domain.order.entity.OrderStatus;
import com.sparta.spartdelivery.domain.order.repository.OrderRepository;
import com.sparta.spartdelivery.domain.order.service.OrderService;
import com.sparta.spartdelivery.domain.store.entity.Store;
import com.sparta.spartdelivery.domain.store.repository.StoreRepository;
import com.sparta.spartdelivery.domain.user.entity.User;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import com.sparta.spartdelivery.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private Order order;

    @Mock
    private User user;

    @Mock
    private Store store;

    @Mock
    private Menu menu;

    @Mock
    private AuthUser authUser;

    @Mock
    private OrderRequestDto orderRequestDto;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class UsersTest {

        @Nested
        class 손님이_주문할_때 {

            @Test
            void 유저의_주문_성공() {

            }

            @Test
            void 비로그인_상태로_주문했을_때_NPE_던지기() {

                authUser = new AuthUser(1L, "", UserRole.USER);
                orderRequestDto = new OrderRequestDto(1L, 1L, null);
                store = Store.builder()
                        .userId(1L)
                        .storeName("")
                        .openTime(LocalTime.of(10, 00, 00))
                        .closeTime(LocalTime.of(14, 00, 00))
                        .minOrderPrice(15000)
                        .statusOpen(true)
                        .statusShutdown(false)
                        .createdAt(LocalDateTime.of(2024, 10, 01, 00, 00, 00))
                        .build();

                // given
                given(userRepository.findById(authUser.getId())).willReturn(Optional.empty());
                given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
                given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));

                // when
                NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                    orderService.sendOrder(authUser, orderRequestDto);
                });

                assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
            }

            @Test
            void 없는_가게에_주문했을_때_NPE_던지기() {

                // when
                when(storeRepository.findById(1L)).thenReturn(Optional.empty());

                // then
                assertThrows(NullPointerException.class, () -> {
                    orderService.sendOrder(authUser, orderRequestDto);
                });
            }

            @Test
            void 없는_메뉴를_주문했을_때_NPE_던지기() {

                // when
                when(menuRepository.findById(1L)).thenReturn(Optional.empty());

                // then
                assertThrows(NullPointerException.class, () -> {
                    orderService.sendOrder(authUser, orderRequestDto);
                });

            }

            @Test
            void 최소주문금액_미만으로_주문했을_때_IE_던지기() {

                authUser = new AuthUser(1L, "", UserRole.USER);
                orderRequestDto = new OrderRequestDto(1L, 1L, OrderStatus.ACCEPTED);
                store = Store.builder()
                        .userId(1L)
                        .storeName("")
                        .openTime(LocalTime.of(12, 00, 00))
                        .closeTime(LocalTime.of(14, 00, 00))
                        .minOrderPrice(15000)
                        .statusOpen(true)
                        .statusShutdown(false)
                        .createdAt(LocalDateTime.of(2024, 10, 01, 00, 00, 00))
                        .build();

                // given
                given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));
                given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
                given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));

                // when
                IllegalAccessException exception = assertThrows(IllegalAccessException.class, () -> {
                    orderService.sendOrder(authUser, orderRequestDto);
                });

                assertEquals("최소 주문 금액 이상 주문해야 합니다.", exception.getMessage());
            }

            @Test
            void 다른_가게의_메뉴를_주문했을_때_IE_던지기() {

            }

            @Test
            void 영업시간_외의_주문은_IE_던지기() {

            }
        }
    }

    @Nested
    class OwnerTest {

        @Nested
        class 사장님이_주문_내역을_조회할_때 {

            @Test
            void 사장님이_주문_내역을_조회한다() {

            }

            @Test
            void 일반_유저가_들어온_주문내역을_요청하면_IE_던지기() {

                // given
                user = new User(1L, "", UserRole.USER);

                // when & then
                when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

                IllegalAccessException thrown = assertThrows(IllegalAccessException.class, () -> {
                    orderService.getAllOrders(authUser);
                });

                assertEquals("가게의 사장님만 접근할 수 있습니다.", thrown.getMessage());
            }

            @Test
            void 들어온_주문내역이_없으면_NPE_던지기() {

                user = new User(1L, "", UserRole.OWNER);
                authUser = new AuthUser(1L, "", UserRole.OWNER); // DTO

                given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));
                given(orderRepository.findById(anyLong())).willReturn(Optional.empty());

                NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                    orderService.getAllOrders(authUser);
                });
                assertEquals("들어온 주문 내역이 없습니다.", exception.getMessage());


            }
        }

        @Nested
        class 사장님이_주문_상태를_수정할_때 {

            @Test
            void 주문_수락하기() {

            }

            @Test
            void 배달_완료하기() {

            }

            @Test
            void 없는_주문을_수락한다면_NPE_처리() {

                // given
                Long orderId = 30L;
                authUser = new AuthUser(1L, "1", UserRole.OWNER);

                // when
                when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

                // then
                assertThrows(NullPointerException.class, () -> {
                    orderService.updateOrderStatus(authUser, orderId, orderRequestDto);
                });
            }

            @Test
            void 없는_주문을_배달완료한다면_NPE_처리() {
                Long orderId = 30L;
                authUser = new AuthUser(1L, "1", UserRole.OWNER);

                when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

                assertThrows(NullPointerException.class, () -> {
                    orderService.updateOrderStatus(authUser, orderId, orderRequestDto);
                });
            }
        }
    }
}