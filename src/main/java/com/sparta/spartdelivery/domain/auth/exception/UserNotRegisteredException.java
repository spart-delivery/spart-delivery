package com.sparta.spartdelivery.domain.auth.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class UserNotRegisteredException extends CommonException {
    public UserNotRegisteredException() {
        super(HttpStatus.BAD_REQUEST, "가입되지 않은 유저입니다.");
    }
}