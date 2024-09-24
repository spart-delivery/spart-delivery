package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PermissionDefinedStoreUpdateException extends CommonException {
    public PermissionDefinedStoreUpdateException() {
        super(HttpStatus.UNAUTHORIZED, "해당 가게에 대한 권한이 없습니다.");
    }
}
