package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class StoreNameIsExitsException extends CommonException {
    public StoreNameIsExitsException() {
        super(HttpStatus.BAD_REQUEST, "해당 이름을 가진 가게가 이미 존재합니다.");
    }
}
