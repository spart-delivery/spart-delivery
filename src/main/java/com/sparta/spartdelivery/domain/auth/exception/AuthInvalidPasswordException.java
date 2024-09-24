package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AuthInvalidPasswordException extends CommonException {
    public AuthInvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
    }
}