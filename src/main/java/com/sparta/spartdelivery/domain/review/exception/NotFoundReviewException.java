package com.sparta.spartdelivery.domain.review.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class NotFoundReviewException extends CommonException {

    public NotFoundReviewException() {
        super(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다.");
    }

}
