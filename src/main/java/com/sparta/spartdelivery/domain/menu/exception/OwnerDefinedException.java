package com.sparta.spartdelivery.domain.menu.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class OwnerDefinedException extends CommonException {
    public OwnerDefinedException(HttpStatus status, String message)
    {super(status,message);}
}
