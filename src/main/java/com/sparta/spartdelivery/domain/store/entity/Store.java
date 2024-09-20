package com.sparta.spartdelivery.domain.store.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, name = "store_name")
    private String storeName;

    @Column(nullable = false, name = "open_time")
    private LocalDateTime openTime;

    @Column(nullable = false, name = "close_time")
    private LocalDateTime closeTime;

    @Column(nullable = false, name = "min_order_price")
    private Integer minOrderPrice;

    @ColumnDefault("1")
    @Column(nullable = false, name = "status_open")
    private boolean statusOpen;

    @ColumnDefault("0")
    @Column(nullable = false, name = "status_shutdown")
    private boolean statusShutdown;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;


}
