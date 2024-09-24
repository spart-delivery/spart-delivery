package com.sparta.spartdelivery.domain.store.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.spartdelivery.domain.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class StoreFindResponseDto {

    private Long storeId;

    private String storeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeTime;

    private Integer minOrderPrice;

    private List<Menu> menuList;
}
