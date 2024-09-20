package com.sparta.spartdelivery.domain.auth.dto.response;

import lombok.*;



@Getter
public class AuthSigninDtoResponse<T> {

    private final String bearerToken;
    private final int statusCode;
    private final String message;
    private final T data;

    public AuthSigninDtoResponse(String bearerToken, int statusCode, String message, T data) {
        this.bearerToken = bearerToken;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }



}
