package com.sparta.spartdelivery.domain.menuCategory.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MenuCategoryDetailResponseDto {
    private final List<String> menuNames;

    public MenuCategoryDetailResponseDto(List<String> menuNames) {
        this.menuNames = menuNames;
    }
}
