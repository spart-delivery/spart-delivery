package com.sparta.spartdelivery.domain.order.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class OwnerOnlyAccessException extends CommonException {

  public OwnerOnlyAccessException(HttpStatus status, String message) {
    super(status, message);
  }
}