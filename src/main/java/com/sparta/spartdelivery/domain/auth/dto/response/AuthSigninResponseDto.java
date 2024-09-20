package com.sparta.spartdelivery.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthSigninResponseDto {
    private final String bearerToken;

    public AuthSigninResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
