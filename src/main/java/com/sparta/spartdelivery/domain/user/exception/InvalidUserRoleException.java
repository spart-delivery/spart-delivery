package com.sparta.spartdelivery.domain.user.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class InvalidUserRoleException extends CommonException {
    public InvalidUserRoleException() {
        super(HttpStatus.BAD_REQUEST, "유효하지 않은 UserRole입니다.");
    }
}
