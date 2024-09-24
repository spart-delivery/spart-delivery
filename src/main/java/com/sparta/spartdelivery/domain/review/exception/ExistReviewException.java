package com.sparta.spartdelivery.domain.review.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ExistReviewException extends CommonException {
    public ExistReviewException() {
        super(HttpStatus.BAD_REQUEST, "해당 주문에 대한 리뷰가 이미 존재합니다.");
    }
}
