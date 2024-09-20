package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class MaxCountStoreException extends CommonException {
    public MaxCountStoreException(HttpStatus status, String message) {
        super(status, message);
    }
}
