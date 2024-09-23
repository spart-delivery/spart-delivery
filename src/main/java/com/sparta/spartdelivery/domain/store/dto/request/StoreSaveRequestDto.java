package com.sparta.spartdelivery.domain.store.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreSaveRequestDto {

    @NotBlank(message = "가게명은 필수 파라미터 입니다.")
    private String storeName;

    // 내가 원하는 방식으로 날짜형태를 받고 싶을 때 사용함
    @NotBlank(message = "오픈시간은 필수 파라미터 입니다.")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String openTime;

    // 내가 원하는 방식으로 날짜형태를 받고 싶을 때 사용함
    @NotBlank(message = "마감시간은 필수 파라미터 입니다.")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String closeTime;

    @NotNull(message = "최소주문금액은 필수 파라미터 입니다.")
    private Integer minOrderPrice;

}
