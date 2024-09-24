package com.sparta.spartdelivery.domain.menuCategory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="category_id")
    private Long categoryId;

    @Column (unique = true)
    private String categoryName;

    public MenuCategory(String categoryName){
        this.categoryName = categoryName;
    }
}
