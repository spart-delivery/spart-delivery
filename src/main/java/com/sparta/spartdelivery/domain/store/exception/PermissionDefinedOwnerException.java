package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PermissionDefinedOwnerException extends CommonException {
    public PermissionDefinedOwnerException(HttpStatus status, String message) {
        super(status, message);
    }
}
