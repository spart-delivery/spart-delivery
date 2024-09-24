package com.sparta.spartdelivery.domain.kakaoUser.dto;

import com.sparta.spartdelivery.domain.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginRequestDto {

    @NotBlank(message = "빈칸은 입력이 불가능합니다.")
    private String userRole;

}
