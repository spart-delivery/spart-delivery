package com.sparta.spartdelivery.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchResponseDto {
    private List<StoreResponseDto> stores;
    private List<MenuResponseDto> menus;

    // 생성자
    public SearchResponseDto(List<StoreResponseDto> storeResponseDtos, List<MenuResponseDto> menuResponseDtos) {
        this.stores = storeResponseDtos;
        this.menus = menuResponseDtos;
    }
}