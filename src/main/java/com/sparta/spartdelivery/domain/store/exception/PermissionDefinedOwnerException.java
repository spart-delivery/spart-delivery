package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PermissionDefinedOwnerException extends CommonException {
    public PermissionDefinedOwnerException() {
        super(HttpStatus.UNAUTHORIZED, "사장님 권한을 가진 유저만 가게를 만들 수 있습니다.");
    }
}
