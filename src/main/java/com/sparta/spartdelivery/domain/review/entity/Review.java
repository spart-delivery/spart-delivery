package com.sparta.spartdelivery.domain.review.entity;

import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long reviewId;

    @Column(name = "starPoint", nullable = false)
    private Integer starPoint;

    @Column(name = "comment", nullable = false)
    private String comment;

    // 다대일 관계 추가 예정.
//    @ManyToOne
//    @JoinColumn(name = "orderId", nullable = false)
//    private Order order;
//
//    @ManyToOne
//    @JoinColumn(name = "storeId", nullable = false)
//    private Store store;


    public Review(ReviewSaveRequestDto reviewSaveRequestDto) {
        this.starPoint = reviewSaveRequestDto.getStarPoint();
        this.comment = reviewSaveRequestDto.getComment();
    }
}
