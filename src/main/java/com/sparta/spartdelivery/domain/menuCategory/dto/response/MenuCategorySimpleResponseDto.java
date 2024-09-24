package com.sparta.spartdelivery.domain.menuCategory.dto.response;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class MenuCategorySimpleResponseDto {
    private final String categoryName;

    public MenuCategorySimpleResponseDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
