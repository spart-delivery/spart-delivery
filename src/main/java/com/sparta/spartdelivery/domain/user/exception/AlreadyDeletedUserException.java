package com.sparta.spartdelivery.domain.user.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AlreadyDeletedUserException extends CommonException {
    public AlreadyDeletedUserException() {
        super(HttpStatus.BAD_REQUEST, "이미 탈퇴한 사용자입니다.");
    }
}
