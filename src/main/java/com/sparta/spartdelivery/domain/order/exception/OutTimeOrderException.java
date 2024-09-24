package com.sparta.spartdelivery.domain.order.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class OutTimeOrderException extends CommonException {

    public OutTimeOrderException(HttpStatus status, String message) {
        super(status, message);
    }
}
