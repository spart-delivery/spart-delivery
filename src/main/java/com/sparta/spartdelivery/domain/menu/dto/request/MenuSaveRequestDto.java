package com.sparta.spartdelivery.domain.menu.dto.request;

import lombok.Getter;

@Getter
public class MenuSaveRequestDto {
    private String menuName;
    private int menuPrice;

    private Long categoryId;

    public MenuSaveRequestDto(String menuName, int menuPrice,Long categoryId) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryId=categoryId;
    }
}
