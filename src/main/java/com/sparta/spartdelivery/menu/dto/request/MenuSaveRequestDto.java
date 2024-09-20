package com.sparta.spartdelivery.menu.dto.request;

import lombok.Getter;

@Getter
public class MenuSaveRequestDto {
    private Long storeId;
    private String menuName;
    private int menuPrice;
}
