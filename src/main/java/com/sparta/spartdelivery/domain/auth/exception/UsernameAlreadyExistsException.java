package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends CommonException {
    public UsernameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자 이름입니다.");
    }
}