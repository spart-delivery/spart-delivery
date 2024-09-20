package com.sparta.spartdelivery.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "order_id")
    private long userId;

    @Column(name = "order_id")
    private long storeId;

    @Column(name = "order_id")
    private long menuId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = null;

    public Order(long user, long store, long menu) {
        this.userId = user;
        this.storeId = store;
        this.menuId = menu;
        this.createdAt = LocalDateTime.now();
        this.status = Status.ORDERED;
    }


    public enum Status {
        ORDERED,
        ACCEPTED,
        COMPLETED
    }

    // 주문 수락 세팅
    public void acceptedOrder() {
        if (this.status == Status.ORDERED) {
            this.status = Status.ACCEPTED;
        }
    }

    // 배달 완료 세팅
    public void completedOrder() {
        if (this.status == Status.ACCEPTED) {
            this.status = Status.COMPLETED;
        }
    }

}