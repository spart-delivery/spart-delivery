package com.sparta.spartdelivery.domain.store.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreEditRequestDto {

    @NotBlank(message = "가게명은 필수 파라미터 입니다.")
    private String storeName;

    @NotBlank(message = "오픈시간은 필수 파라미터 입니다.")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String openTime;

    @NotBlank(message = "마감시간은 필수 파라미터 입니다.")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String closeTime;

    @NotNull(message = "최소주문금액은 필수 파라미터 입니다.")
    private Integer minOrderPrice;

}
