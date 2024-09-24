package com.sparta.spartdelivery.domain.review.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class DoNotDeleteReviewException extends CommonException {
    public DoNotDeleteReviewException() {
        super(HttpStatus.BAD_REQUEST, "본인이 작성한 리뷰만 수정 및 삭제할 수 있습니다.");
    }
}
