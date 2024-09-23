package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AuthException extends CommonException {

    public AuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

