package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PasswordException extends CommonException {
    public PasswordException(HttpStatus status, String message) {
        super(status, message);
    }
}
