package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class MaxCountStoreException extends CommonException {
    public MaxCountStoreException() {
        super(HttpStatus.BAD_REQUEST, "사장님은 가게를 최대 3개까지만 운영할 수 있습니다.");
    }
}
