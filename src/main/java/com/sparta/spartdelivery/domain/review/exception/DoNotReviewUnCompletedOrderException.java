package com.sparta.spartdelivery.domain.review.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class DoNotReviewUnCompletedOrderException extends CommonException {
    public DoNotReviewUnCompletedOrderException() {
        super(HttpStatus.BAD_REQUEST, "리뷰는 완료된 주문에 한해서만 작성할 수 있습니다.");
    }
}
