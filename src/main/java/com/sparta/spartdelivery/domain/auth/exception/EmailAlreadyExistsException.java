package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CommonException {
    public EmailAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
    }
}