package com.sparta.spartdelivery.domain.auth.dto.response;

import lombok.Getter;

@Getter
//  토큰 정보를 담는 클래스
public class AuthSigninResponseDto {
    private final String bearerToken;

    public AuthSigninResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
