package com.sparta.spartdelivery.domain.user.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class SamePasswordException extends CommonException {
    public SamePasswordException() {
        super(HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
    }
}
