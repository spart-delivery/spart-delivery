package com.sparta.spartdelivery.domain.menu.dto.response;

import lombok.Getter;

@Getter

public class MenuUpdateResponseDto {
    private final String menuName;
    private final int menuPrice;

    public MenuUpdateResponseDto(String menuName, int menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }
}
