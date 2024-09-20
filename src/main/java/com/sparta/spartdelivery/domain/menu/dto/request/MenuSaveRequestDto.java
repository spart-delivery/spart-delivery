package com.sparta.spartdelivery.domain.menu.dto.request;

import lombok.Getter;

@Getter
public class MenuSaveRequestDto {
    private String menuName;
    private int menuPrice;
    private Long storeId;
}
