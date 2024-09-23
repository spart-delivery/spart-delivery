package com.sparta.spartdelivery.domain.menuCategory.dto.response;

import lombok.Getter;

@Getter
public class MenuCategorySaveResponseDto {

    private final String categoryName;

    public MenuCategorySaveResponseDto( String categoryName) {
        this.categoryName = categoryName;
    }
}
