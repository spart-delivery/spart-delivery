package com.sparta.spartdelivery.domain.user.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CommonException {

    public UserNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다.");
    }
}

