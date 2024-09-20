package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class StoreNameIsExitsException extends CommonException {
    public StoreNameIsExitsException(HttpStatus status, String message) {
        super(status, message);
    }
}
