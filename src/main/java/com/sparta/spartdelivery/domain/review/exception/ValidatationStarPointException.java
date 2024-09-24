package com.sparta.spartdelivery.domain.review.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ValidatationStarPointException extends CommonException {
    public ValidatationStarPointException() {
        super(HttpStatus.BAD_REQUEST, "별점은 1 ~ 5 만 입력값만 입력할 수 있습니다.");
    }
}
