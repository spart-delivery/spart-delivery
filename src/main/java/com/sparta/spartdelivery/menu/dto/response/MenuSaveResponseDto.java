package com.sparta.spartdelivery.menu.dto.response;

import lombok.Getter;

@Getter
public class MenuSaveResponseDto {
    private final Long storeId;
    private final Long menuId;
    private final String menuName;
    private final int menuPrice;

    public MenuSaveResponseDto(Long storeId, Long menuId, String menuName, int menuPrice) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }
}
