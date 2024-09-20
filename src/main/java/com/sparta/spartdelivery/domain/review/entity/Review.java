package com.sparta.spartdelivery.domain.review.entity;

import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewEditRequestDto;
import com.sparta.spartdelivery.domain.review.dto.requestDto.ReviewSaveRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "star_point", nullable = false)
    private Integer starPoint;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;
    

    // 다대일 관계 추가 예정.
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;


    public Review(ReviewSaveRequestDto reviewSaveRequestDto) {
        this.starPoint = reviewSaveRequestDto.getStarPoint();
        this.comment = reviewSaveRequestDto.getComment();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(ReviewEditRequestDto reviewEditRequestDto) {
        this.starPoint = reviewEditRequestDto.getStarPoint();
        this.comment = reviewEditRequestDto.getComment();
        this.modifiedAt = LocalDateTime.now();
    }
}
