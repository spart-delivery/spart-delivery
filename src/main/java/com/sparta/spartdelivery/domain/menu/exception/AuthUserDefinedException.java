package com.sparta.spartdelivery.domain.menu.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AuthUserDefinedException extends CommonException {
    public AuthUserDefinedException(HttpStatus status, String message)
    {super(status,message);}
}
