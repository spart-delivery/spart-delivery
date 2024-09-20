package com.sparta.spartdelivery.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthSignupDtoResponse {

    private final String bearerToken;

    public AuthSignupDtoResponse(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
