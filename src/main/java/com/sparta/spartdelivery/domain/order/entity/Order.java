package com.sparta.spartdelivery.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public void initOrder(long user, long store, long menu) {
        this.userId = user;
        this.storeId = store;
        this.menuId = menu;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.ORDERED;
    }

    public Order(long user, long store, long menu) {
        initOrder(user, store, menu);
    }

    public void acceptedOrder() {
        this.status = OrderStatus.ACCEPTED;
    }

    public void completedOrder() {
        this.status = OrderStatus.COMPLETED;
    }
}
