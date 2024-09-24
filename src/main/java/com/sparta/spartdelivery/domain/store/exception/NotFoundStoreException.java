package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class NotFoundStoreException extends CommonException {

    public NotFoundStoreException() {
        super(HttpStatus.BAD_REQUEST, "상점을 찾을 수 없습니다.");
    }
}
