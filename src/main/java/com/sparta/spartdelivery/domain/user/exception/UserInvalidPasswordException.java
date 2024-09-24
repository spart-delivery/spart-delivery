package com.sparta.spartdelivery.domain.user.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class UserInvalidPasswordException extends CommonException {
    public UserInvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
    }

}
