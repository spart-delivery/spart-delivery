package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PermissionDefinedStoreUpdateException extends CommonException {
    public PermissionDefinedStoreUpdateException(HttpStatus status, String message) {
        super(status, message);
    }
}
