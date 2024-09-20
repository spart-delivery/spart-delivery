package com.sparta.spartdelivery.domain.store.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreEditRequestDto {

    private Long storeId;

    private String storeName;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private Integer minOrderPrice;

}
