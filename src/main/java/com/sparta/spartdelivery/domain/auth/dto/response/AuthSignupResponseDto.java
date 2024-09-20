package com.sparta.spartdelivery.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthSignupResponseDto {
    private final String bearerToken;

    public AuthSignupResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}

