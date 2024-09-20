package com.sparta.spartdelivery.domain.store.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StoreSaveRequestDto {

    private String storeName;

    // 내가 원하는 방식으로 날짜형태를 받고 싶을 때 사용함
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openTime;

    // 내가 원하는 방식으로 날짜형태를 받고 싶을 때 사용함
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    private Integer minOrderPrice;

    private Long userId;

}
