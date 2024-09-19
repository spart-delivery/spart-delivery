package com.sparta.spartdelivery.domain.user.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends CommonException {

    public InvalidRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

