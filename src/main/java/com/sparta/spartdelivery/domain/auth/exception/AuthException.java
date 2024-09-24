package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AuthException extends CommonException {

    public AuthException() {
        super(HttpStatus.UNAUTHORIZED, "@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
    }
}

