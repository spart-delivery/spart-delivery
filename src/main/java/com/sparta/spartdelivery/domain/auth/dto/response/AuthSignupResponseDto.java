package com.sparta.spartdelivery.domain.auth.dto.response;

import lombok.Getter;

@Getter
//  토큰 정보를 담는 클래스
public class AuthSignupResponseDto {
    private final String bearerToken;

    public AuthSignupResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}

